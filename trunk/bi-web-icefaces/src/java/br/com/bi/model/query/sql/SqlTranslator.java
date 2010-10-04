/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.query.sql;

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
public class SqlTranslator implements MetadataEntityVisitor {

    private static final String PREFIX_ROWS = "r";
    private static final String PREFIX_COLUMNS = "c";
    private StringBuilder sql = new StringBuilder();
    private Stack<Integer> stack = new Stack<Integer>();

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
        StringBuilder fragment = new StringBuilder();

        // ===============
        // = traduz o nó =
        // ===============

        if (!node.isRoot()) {
            stack.push(node.getPai().getChildren().indexOf(node));

            if (node.getMetadataEntity() instanceof Level) {
                visit((Level) node.getMetadataEntity());
            } else if (node.getMetadataEntity() instanceof Measure) {
                visit((Measure) node.getMetadataEntity());
            } else if (node.getMetadataEntity() instanceof Filter) {
                visit((Filter) node.getMetadataEntity());
            }

            fragment.append(")");
            fragment.append(" AS ").append(columnAlias(columnPrefix));
        }

        // ====================
        // = traduz os filhos =
        // ====================

        if (!node.getChildren().isEmpty()) {
            if (!node.isRoot()) {
                fragment.append(", ");
            }

            for (int i = 0; i < node.getChildren().size(); i++) {
                Node filho = node.getChildren().get(i);

                fragment.append(translateNodeToSql(columnPrefix, filho));

                if (i < node.getChildren().size() - 1) {
                    fragment.append(", ");
                }
            }
        }

        if (!node.isRoot()) {
            stack.pop();
        }

        return fragment.toString();
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
            sql.append(selectExpression(measure.getCube().
                    getTable(), measure.getColumn()));
            sql.append(" ELSE null END");
        } else {
            sql.append(selectExpression(measure.getCube().
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
        sql.append(selectExpression(level.getTable(), level.getCodeProperty().
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
    public String selectExpression(String table, String column) {
        StringBuilder selectExpression = new StringBuilder();

        selectExpression.append(table).append(".").append(column);

        return selectExpression.toString();
    }

    /**
     * Retorna o apelido da coluna que está sendo traduzida neste momento.
     * Isto é feito levando-se em conta a posição do nó na árvore da consulta.
     * @return
     */
    public String columnAlias(String aliasPrefix) {
        StringBuilder columnAlias = new StringBuilder(aliasPrefix);

        columnAlias.append("_");

        for (int i = 0; i < stack.size(); i++) {
            columnAlias.append(stack.peek());

            if (i < stack.size() - 1) {
                columnAlias.append("_");
            }
        }

        return columnAlias.toString();
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
            for (Level lowerLevel : lowerLevels(topLevel)) {
                tables.add(tableExpression(lowerLevel.getSchema(), lowerLevel.
                        getTable()));
            }
        }

        StringBuilder tableExpression = new StringBuilder();

        for (int i = 0; i < tables.size(); i++) {
            tableExpression.append(tables.get(i));

            if (i < tables.size() - 1) {
                tableExpression.append(",");
            }
        }

        return tableExpression.toString();
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
        throw new UnsupportedOperationException("Not yet implemented");
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
            // se o nível está acima do que já está no mapa então ele é um "top level".
            if (!topLevels.containsKey(level.getDimension()) || isHigherLevel(level, topLevels.
                    get(level.getDimension()))) {
                topLevels.put(level.getDimension(), level);
            }
        }

        return topLevels.values();
    }

    private List<Level> lowerLevels(Level higherLevel) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Diz se este nível denotado por lower encontra-se hierarquicamente abaixo do nível
     * denotado por higher.
     * @param level
     * @return
     */
    public boolean isLowerLevel(Level lower, Level higher) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Diz se este nível denotado por higher encontra-se hierarquicamente acima do nível
     * denotado por lower.
     * @param level
     * @return
     */
    public boolean isHigherLevel(Level higher, Level lower) {
        throw new UnsupportedOperationException("Not yet implemented");
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
