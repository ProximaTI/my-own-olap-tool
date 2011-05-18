/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luiz
 */
public class TranslationUtils {

    public static String encloseString(String string) {
        // TODO rdbms driver (refactory)
        StringBuilder sb = new StringBuilder("'");
        sb.append(string).append("'");
        return sb.toString();
    }

    public static String discloseString(String string) {
        StringBuilder sb = new StringBuilder(string);

        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String likeWildcard() {
        // TODO rdbms driver (refactory)
        return "%";
    }

    public static String extractName(String expression) {
        String patternStr = "\\[(.*)\\]";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    public static String extractColumn(String expression) {
        String patternStr = "\"(.*)\"";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    /**
     * Retorna uma string do tipo tabela + . + coluna.
     * @param table
     * @param column
     * @return
     */
    public static String columnExpression(String table, String column) {
        // TODO rdbms driver (refactory)
        StringBuilder sb = new StringBuilder();
        sb.append(table).append(".").append(column);
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
    public static String tableExpression(String schema, String table) {
        // TODO rdbms driver (refactory)
        StringBuilder sb = new StringBuilder();
        sb.append(schema).append(".").append(table);
        return sb.toString();
    }
}
