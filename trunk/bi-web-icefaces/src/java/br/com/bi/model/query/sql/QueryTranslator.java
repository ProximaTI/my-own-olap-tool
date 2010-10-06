/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.query.sql;

import br.com.bi.model.dao.DimensaoDao;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.MetadataEntityVisitor;
import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.query.Query;
import br.com.bi.model.entity.query.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Luiz
 */
public class QueryTranslator implements MetadataEntityVisitor, Constants {

    private Stack<Integer> stack = new Stack<Integer>();
    private StringBuilder sql = new StringBuilder();
    private DimensaoDao dimensaoDao;

    /**
     * @return the dimensaoDao
     */
    public DimensaoDao getDimensaoDao() {
        return dimensaoDao;
    }

    /**
     * @param dimensaoDao the dimensaoDao to set
     */
    public void setDimensaoDao(DimensaoDao dimensaoDao) {
        this.dimensaoDao = dimensaoDao;
    }

    /**
     * Traduz uma consulta em uma instrução SQL.
     * @param query
     * @return
     */
    public String translateToSql(Query query) {
        sql = new StringBuilder("SELECT ");

        sql.append(translateNodeToSql(PREFIX_ROWS, query.getRows())).append(", ");
        sql.append(translateNodeToSql(PREFIX_COLUMNS, query.getColumns()));

        sql.append(" FROM ");

        sql.append(fromExpression(query));

        sql.append(" WHERE ");

        sql.append(whereExpression(query));

        if (query.getFilterExpression() != null) {
            sql.append(translateExpression(query.getFilterExpression()));
        }

        return sql.toString();
    }

    /**
     * Translate um nó em um fragmento de instrução SQL.
     * @param node
     * @return
     */
    private String translateNodeToSql(String columnPrefix, Node node) {
        StringBuilder sb = new StringBuilder();

        // ===============
        // = traduz o nó =
        // ===============

        if (!node.isRoot()) {
            stack.push(node.getParent().getChildren().indexOf(node));

            if (node.getMetadataEntity() instanceof Level) {
                visit((Level) node.getMetadataEntity());
            } else if (node.getMetadataEntity() instanceof Measure) {
                visit((Measure) node.getMetadataEntity());
            } else if (node.getMetadataEntity() instanceof Filter) {
                visit((Filter) node.getMetadataEntity());
            }

            sb.append(")");
            sb.append(" AS ").append(columnAlias(columnPrefix));
        }

        // ====================
        // = traduz os filhos =
        // ====================

        if (!node.getChildren().isEmpty()) {
            if (!node.isRoot()) {
                sb.append(", ");
            }

            for (int i = 0; i < node.getChildren().size(); i++) {
                Node filho = node.getChildren().get(i);

                sb.append(translateNodeToSql(columnPrefix, filho));

                if (i < node.getChildren().size() - 1) {
                    sb.append(", ");
                }
            }
        }

        if (!node.isRoot()) {
            stack.pop();
        }

        return sb.toString();
    }

    // =====================
    // ====== Visitor ======
    // =====================
    /**
     * Traduz uma métrica em um fragmento de instrução SQL.
     * @param measure
     */
    public void visit(Measure measure) {

        sql.append(measure.getFuncao()).append("(");

        if (measure.getFilterExpression() != null) {
            sql.append("CASE WHEN ");
            sql.append(translateExpression(measure.getFilterExpression()));
            sql.append(" THEN ");
            sql.append(columnExpression(measure.getCube().
                    getTable(), measure.getColumn()));
            sql.append(" ELSE null END");
        } else {
            sql.append(columnExpression(measure.getCube().
                    getTable(), measure.getColumn()));
        }
    }

    /**
     * Traduz um filtro em um fragmento de instrução SQL.
     * @param filter
     */
    public void visit(Filter filter) {
        sql.append("CASE WHEN ");
        sql.append(translateExpression(filter.getExpression()));
        sql.append(" THEN 1 ELSE 0 END");
    }

    /**
     * Traduz um nível em um fragmento de instrução SQL.
     * @param level
     */
    public void visit(Level level) {
        sql.append(columnExpression(level.getTable(), level.getCodeProperty().
                getColumn()));
    }

    // ===========================
    // ====== Gramática SQL ======
    // ===========================
    /**
     * Retorna uma string do tipo tabela + . + coluna.
     * @param table
     * @param column
     * @return
     */
    public String columnExpression(String table, String column) {
        StringBuilder sb = new StringBuilder();

        sb.append(table).append(".").append(column);

        return sb.toString();
    }

    /**
     * Retorna o apelido da coluna que está sendo traduzida neste momento.
     * Isto é feito levando-se em conta a posição do nó na árvore da consulta.
     * @return
     */
    public String columnAlias(String aliasPrefix) {
        StringBuilder sb = new StringBuilder(aliasPrefix);

        sb.append("_");

        for (int i = 0; i < stack.size(); i++) {
            sb.append(stack.peek());

            if (i < stack.size() - 1) {
                sb.append("_");
            }
        }

        return sb.toString();
    }

    /**
     * Retorna a expressão que corresponde à cláusula FROM do SELECT que está sendo contruído.
     * As tabelas desta expressão correspondem aos níveis presentes nos nós da consulta, tanto
     * explicitamente referenciados quanto os níveis presentes nos diversos filtros.
     * 
     * @param query
     * @return
     */
    private String fromExpression(Query query) {
        List<String> tables = new ArrayList<String>();

        tables.add(tableExpression(query.getCube().getSchema(), query.getCube().
                getTable()));

        for (Level topLevel : topLevels(query)) {
            tables.add(tableExpression(topLevel.getSchema(), topLevel.getTable()));
            for (Level lowerLevel : dimensaoDao.lowerLevels(topLevel.getId())) {
                tables.add(tableExpression(lowerLevel.getSchema(), lowerLevel.
                        getTable()));
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tables.size(); i++) {
            sb.append(tables.get(i));

            if (i < tables.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    /**
     * Retorna a expressão corresponde à referência para uma consulta.
     * Normalmente esta expressão é denotada por "esquema.tabela".
     * 
     * @param schema
     * @param table
     * @return
     */
    private String tableExpression(String schema, String table) {
        StringBuilder sb = new StringBuilder();

        sb.append(schema).append(".").append(table);

        return sb.toString();
    }

    private String whereExpression(Query query) {
        List<String> joins = new ArrayList<String>();

        // do nível mais alto vai descendo e adicionando os joins até chegar o nível
        // mais baixo e assim juntá-lo ao cubo
        for (Level topLevel : topLevels(query)) {

            List<Level> lowerLevels = dimensaoDao.lowerLevels(topLevel.getId());

            List<String> buf = new ArrayList<String>();

            for (Level lowerLevel : lowerLevels) {

                buf.add(columnExpression(lowerLevel.getTable(), lowerLevel.
                        getCodeProperty().getColumn()));

                // de duas em duas colunas, escreve no buffer o join
                if (buf.size() == 2) {
                    joins.add(buf.get(0) + " = " + buf.get(1));
                    buf = new ArrayList<String>();
                }
            }

            Level lowestLevel = lowerLevels.get(lowerLevels.size() - 1);

            for (CubeLevel level : query.getCube().getCubeLevels()) {
                if (level.getNivel().getId() == lowestLevel.getId()) {
                    joins.add(
                            columnExpression(query.getCube().getTable(), level.
                            getColunaJuncao())
                            + " = " + columnExpression(lowestLevel.getTable(), lowestLevel.
                            getJoinColumn()));
                }
            }
        }

        // TODO converter joins em uma string

        return null;
    }

    // =============================
    // ======== Utilitários ========
    // =============================
    /**
     * Traduz uma expressão de filtro em um fragmento SQL.
     * @param expression
     * @return
     */
    public String translateExpression(String expression) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Retorna os níveis utilizados na consulta que são os mais altos em suas dimensões.
     * @param query
     * @return
     */
    private Collection<Level> topLevels(Query query) {
        Map<Dimension, Level> topLevels = new HashMap<Dimension, Level>();

        for (Level level : findLevelsPresent(query)) {
            Dimension dimension = dimensaoDao.findByLevelId(level.getId());

            // se o nível está acima do que já está no mapa então ele é um "top level".
            if (!topLevels.containsKey(dimension) || level.getIndice() > topLevels.
                    get(dimension).getIndice()) {
                topLevels.put(dimension, level);
            }
        }

        return topLevels.values();
    }

    /**
     * Retorna os nível presentes na consulta, seja explicitamente em um nó, ou indiretamente
     * através de um nó filtro, ou filtro de métrica, ou ainda filtro na consulta.
     * @param query
     * @return
     */
    private List<Level> findLevelsPresent(Query query) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
