/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.query.sql;

import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.model.entity.query.Node;
import br.com.bi.model.entity.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Classe responsável por tomar uma consulta e traduzí-la em uma instrução SQL.
 * @author Luiz
 */
public class QueryTranslator implements Constants {

    private Query query;
    private Stack<Integer> stack = new Stack<Integer>();

    public QueryTranslator(Query query) {
        this.query = query;
    }

    /**
     * Traduz uma consulta em uma instrução SQL.
     * @param query
     * @return
     */
    public String translate() {
        StringBuilder sql = new StringBuilder("SELECT ");

        sql.append(translateNode(ROWS_PREFIX, query.getRows()));

        sql.append(", ");

        sql.append(translateNode(COLUMNS_PREFIX, query.getColumns()));

        sql.append(" FROM ");

        sql.append(fromExpression());

        sql.append(" WHERE ");

        sql.append(whereExpression());

        if (query.getFilterExpression() != null) {
            sql.append(translateExpression(query.getFilterExpression()));
        }

        sql.append(" GROUP BY ");

        sql.append(groupByExpression());

        return sql.toString();
    }

    /**
     * Traduz um nó de uma consulta em um fragmento de instrução SQL.
     * @param node
     * @return
     */
    private String translateNode(String axisPrefix, Node node) {
        StringBuilder sb = new StringBuilder();

        // ===============
        // = traduz o nó =
        // ===============

        if (!node.isRoot()) {
            stack.push(node.getParent().getChildren().indexOf(node));

            if (node.getMetadataEntity() instanceof Level) {
                sb.append(translateLevel((Level) node.getMetadataEntity()));
            } else if (node.getMetadataEntity() instanceof Measure) {
                sb.append(translateMeasure((Measure) node.getMetadataEntity()));
            } else if (node.getMetadataEntity() instanceof Filter) {
                sb.append(translateFilter((Filter) node.getMetadataEntity()));
            }

            sb.append(" AS ").append(columnAlias(axisPrefix));
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

                sb.append(translateNode(axisPrefix, filho));

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

    /**
     * Traduz uma métrica em um fragmento de instrução SQL.
     * @param measure
     */
    public String translateMeasure(Measure measure) {
        StringBuilder sb = new StringBuilder();

        sb.append(measure.getAggregateFunction()).append("(");

        if (measure.getFilterExpression() != null) {
            sb.append("CASE WHEN ");
            sb.append(translateExpression(measure.getFilterExpression()));
            sb.append(" THEN ");
            sb.append(columnExpression(query.getCube().getTableName(),
                    measure.getColumnName()));
            sb.append(" ELSE null END");
        } else {
            sb.append(columnExpression(query.getCube().getTableName(),
                    measure.getColumnName()));
        }

        sb.append(")");

        return sb.toString();
    }

    /**
     * Traduz um filtro em um fragmento de instrução SQL.
     * @param filter
     */
    public String translateFilter(Filter filter) {
        StringBuilder sb = new StringBuilder();

        sb.append("CASE WHEN ");
        sb.append(translateExpression(filter.getExpression()));
        sb.append(" THEN 1 ELSE 0 END");

        return sb.toString();
    }

    /**
     * Traduz um nível em um fragmento de instrução SQL.
     * @param level
     */
    public String translateLevel(Level level) {
        StringBuilder sb = new StringBuilder();

        sb.append(columnExpression(level.getTableName(),
                level.getCodeProperty().getColumnName()));

        return sb.toString();
    }

    // =================
    // = Gramática SQL =
    // =================
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
     * @return
     */
    private String fromExpression() {
        List<String> tables = new ArrayList<String>();

        tables.add(tableExpression(query.getCube().getSchemaName(),
                query.getCube().getTableName()));

        for (Level topLevel : topLevels()) {
            for (Level lowerLevel : topLevel.getLowerLevels()) {
                tables.add(tableExpression(lowerLevel.getSchemaName(),
                        lowerLevel.getTableName()));
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tables.size(); i++) {
            sb.append(tables.get(i));

            if (i < tables.size() - 1) {
                sb.append(", ");
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

    /**
     * Produz a cláusula WHERE da consulta, baseado nos joins resultantes das referências
     * para os níveis da consulta.
     * @return
     */
    private String whereExpression() {
        List<String> joins = new ArrayList<String>();

        List<Level> topLevels = topLevels();

        // do nível mais alto vai descendo e adicionando os joins até chegar o nível
        // mais baixo e assim juntá-lo ao cubo
        for (Level topLevel : topLevels) {
            List<Level> lowerLevels = topLevel.getLowerLevels();

            for (int i = 0; i < lowerLevels.size(); i++) {
                // de duas em duas colunas, coloca o join na lista
                if (i > 0 & (i + 1) % 2 == 0) {
                    String upperColumn =
                            columnExpression(lowerLevels.get(i - 1).getTableName(),
                            lowerLevels.get(i
                            - 1).getCodeProperty().getColumnName());

                    String thisColumn =
                            columnExpression(lowerLevels.get(i).getTableName(),
                            lowerLevels.get(i).getUpperLevelJoinColumn());

                    joins.add(thisColumn + " = " + upperColumn);
                }
            }

            // o nível mais baixo (cujo índice é o maior) é o nível que faz junção com o cubo,
            // e indiretamente liga todos os níveis superiores também.
            Collections.sort(lowerLevels, new Comparator<Level>()  {

                public int compare(Level level1, Level level2) {
                    return Integer.valueOf(level1.getIndice()).compareTo(Integer.valueOf(level2.getIndice()));
                }
            });

            Level lowestLevel = lowerLevels.get(lowerLevels.size() - 1);

            for (CubeLevel level : query.getCube().getCubeLevelList()) {
                if (level.getLevel().getId() == lowestLevel.getId()) {
                    joins.add(columnExpression(query.getCube().getTableName(),
                            level.getJoinColumn()) + " = "
                            + columnExpression(lowestLevel.getTableName(),
                            lowestLevel.getCodeProperty().getColumnName()));
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        if (joins.isEmpty()) {
            sb.append("1 = 1");
        } else {
            for (int i = 0; i < joins.size(); i++) {
                sb.append(joins.get(i));

                if (i < joins.size() - 1) {
                    sb.append(" AND ");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Produz a cláusula GROUP BY da consulta.
     * @return
     */
    private String groupByExpression() {
        StringBuilder sb = new StringBuilder();

        List<Level> levelsPresent = levelsPresent();

        for (int i = 0; i < levelsPresent.size(); i++) {
            Level level = levelsPresent.get(i);

            sb.append(columnExpression(level.getTableName(),
                    level.getCodeProperty().getColumnName()));

            if (i < levelsPresent.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    // ===============
    // = Utilitários =
    // ===============
    /**
     * Traduz uma expressão de filtro em um fragmento SQL.
     * @param expression
     * @return
     */
    public String translateExpression(String expression) {
        return expression;
    }

    /**
     * Retorna os níveis utilizados na consulta que são os mais altos em suas dimensões.
     * @param query
     * @return
     */
    private List<Level> topLevels() {
        Map<Integer, Level> topLevels = new HashMap<Integer, Level>();

        List<Level> levelsPresent = levelsPresent();

        for (Level level : levelsPresent) {
            Dimension dimension = level.getDimension();

            // se o nível está acima do que já está no mapa então ele é um "top level".
            if (!topLevels.containsKey(dimension.getId())
                    || level.getIndice()
                    < topLevels.get(dimension.getId()).getIndice()) {
                topLevels.put(dimension.getId(), level);
            }
        }

        return new ArrayList<Level>(topLevels.values());
    }

    /**
     * Retorna os níveis presentes na consulta, seja explicitamente em um nó, ou indiretamente
     * através de um nó filtro, ou filtro de métrica, ou ainda filtro na consulta.
     * @param query
     * @return
     */
    private List<Level> levelsPresent() {
        List<Level> bucket = new ArrayList<Level>();

        extractLevels(bucket, query.getRows());
        extractLevels(bucket, query.getColumns());

        return bucket;
    }

    /**
     * Extrai os níveis referenciados numa consulta, seja explicitamente em um nó,
     * os através de um filtro.
     *
     * @param levelsBucket
     * @param node
     */
    private void extractLevels(List<Level> levelsBucket, Node node) {
        if (node.getMetadataEntity() instanceof Level) {
            levelsBucket.add((Level) node.getMetadataEntity());
        }

        for (Node child : node.getChildren()) {
            extractLevels(levelsBucket, child);
        }
    }
}
