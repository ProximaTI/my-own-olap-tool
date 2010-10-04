/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.query.sql;

import br.com.bi.model.entity.metadata.MetadataEntityVisitor;
import br.com.bi.model.entity.metadata.Filtro;
import br.com.bi.model.entity.metadata.Metrica;
import br.com.bi.model.entity.metadata.Nivel;
import br.com.bi.model.entity.query.Consulta;
import br.com.bi.model.entity.query.No;
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
     * @param consulta
     * @return
     */
    public String translateToSql(Consulta consulta) {
        sql = new StringBuilder("SELECT ");

        sql.append(translateNodeToSql(PREFIX_ROWS, consulta.getLinha())).append(", ");
        sql.append(translateNodeToSql(PREFIX_COLUMNS, consulta.getColuna()));

        sql.append(" FROM ");

        sql.append(fromClause(consulta));

        sql.append(consulta.getCubo().getEsquema()).append(".").append(consulta.
                getCubo().getTabela());

        if (consulta.getExpressaoFiltro() != null) {
            sql.append(translateExpressionToSql(consulta.getExpressaoFiltro()));
        }

        return sql.toString();
    }

    /**
     * Translate um nó em um fragmento de instrução SQL.
     * @param no
     * @return
     */
    private String translateNodeToSql(String columnPrefix, No no) {
        StringBuilder fragment = new StringBuilder();

        // ===============
        // = traduz o nó =
        // ===============

        if (!no.isRaiz()) {
            stack.push(no.getPai().getFilhos().indexOf(no));

            if (no.getMetadataEntity() instanceof Nivel) {
                visit((Nivel) no.getMetadataEntity());
            } else if (no.getMetadataEntity() instanceof Metrica) {
                visit((Metrica) no.getMetadataEntity());
            } else if (no.getMetadataEntity() instanceof Filtro) {
                visit((Filtro) no.getMetadataEntity());
            }

            fragment.append(")");
            fragment.append(" AS ").append(returnAlias(columnPrefix));
        }

        // ====================
        // = traduz os filhos =
        // ====================

        if (!no.getFilhos().isEmpty()) {
            if (!no.isRaiz()) {
                fragment.append(", ");
            }

            for (int i = 0; i < no.getFilhos().size(); i++) {
                No filho = no.getFilhos().get(i);

                fragment.append(translateNodeToSql(columnPrefix, filho));

                if (i < no.getFilhos().size() - 1) {
                    fragment.append(", ");
                }
            }
        }

        if (!no.isRaiz()) {
            stack.pop();
        }

        return fragment.toString();
    }

    // ===========================
    // ======= Utilitários =======
    // ===========================
    /**
     * Retorna uma string do tipo tabela + . + coluna.
     * @param table
     * @param column
     * @return
     */
    public String returnTableColumn(String table, String column) {
        StringBuilder fragment = new StringBuilder();

        fragment.append(table).append(".").append(column);

        return fragment.toString();
    }

    /**
     * Traduz uma expressão de filtro em um fragmento SQL.
     * @param expression
     * @return
     */
    public String translateExpressionToSql(String expression) {
        return expression;
    }

    /**
     * Retorna o apelido da coluna que está sendo traduzida neste momento.
     * Isto é feito levando-se em conta a posição do nó na árvore da consulta.
     * @return
     */
    public String returnAlias(String columnPrefix) {
        StringBuilder fragment = new StringBuilder(columnPrefix);

        fragment.append("_");

        for (int i = 0; i < stack.size(); i++) {
            fragment.append(stack.peek());

            if (i < stack.size() - 1) {
                fragment.append("_");
            }
        }

        return fragment.toString();
    }

    private String fromClause(Consulta consulta) {


        return null;
    }

    // =====================
    // ====== Visitor ======
    // =====================

    /**
     * Traduz uma métrica em um fragmento de instrução SQL.
     * @param metrica
     */
    public void visit(Metrica metrica) {

        sql.append(metrica.getFuncao()).append("(");

        if (metrica.getExpressaoFiltro() != null) {
            sql.append("CASE WHEN ");
            sql.append(translateExpressionToSql(metrica.getExpressaoFiltro()));
            sql.append(" THEN ");
            sql.append(returnTableColumn(metrica.getCubo().
                    getTabela(), metrica.getColuna()));
            sql.append(" ELSE null END");
        } else {
            sql.append(returnTableColumn(metrica.getCubo().
                    getTabela(), metrica.getColuna()));
        }
    }

    /**
     * Traduz um filtro em um fragmento de instrução SQL.
     * @param filtro
     */
    public void visit(Filtro filtro) {
        sql.append("CASE WHEN ");
        sql.append(translateExpressionToSql(filtro.getExpressao()));
        sql.append(" THEN 1 ELSE 0 END");
    }

    /**
     * Traduz um nível em um fragmento de instrução SQL.
     * @param nivel
     */
    public void visit(Nivel nivel) {
        sql.append(returnTableColumn(nivel.getTabela(), nivel.
                getPropriedadeCodigo().getColuna()));
    }
}
