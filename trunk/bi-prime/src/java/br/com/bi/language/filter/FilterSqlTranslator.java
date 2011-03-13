/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.filter;

import br.com.bi.model.Application;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luiz
 */
public class FilterSqlTranslator implements FilterParserVisitor {

    public static void main(String args[]) {
        InputStream in = new ByteArrayInputStream(
                ("[teste] e n\u00e3o [Produto] > ((-1 / (100.8 + 2)) * [teste])").getBytes());

        FilterParser parser = new FilterParser(in);

        try {
            SimpleNode node = parser.filterExpression();

            node.dump(" ");

            StringBuilder sb = new StringBuilder();

            FilterSqlTranslator translator = new FilterSqlTranslator();
            translator.visit(node, sb);

            System.out.println(sb.toString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

    public void visit(Node node, StringBuilder data) {
        if (node instanceof FilterExpression) {
            visit((FilterExpression) node, data);
        }

        if (node instanceof Measure) {
            visit((Measure) node, data);
        }

        if (node instanceof Filter) {
            visit((Filter) node, data);
        }

        if (node instanceof Disjunction) {
            visit((Disjunction) node, data);
        }

        if (node instanceof Conjunction) {
            visit((Conjunction) node, data);
        }

        if (node instanceof Negation) {
            visit((Negation) node, data);
        }

        if (node instanceof Comparison) {
            visit((Comparison) node, data);
        }

        if (node instanceof Level) {
            visit((Level) node, data);
        }

        if (node instanceof Property) {
            visit((Property) node, data);
        }

        if (node instanceof RelationalOperator) {
            visit((RelationalOperator) node, data);
        }

        if (node instanceof ArithmeticExpression) {
            visit((ArithmeticExpression) node, data);
        }

        if (node instanceof Date) {
            visit((Date) node, data);
        }

        if (node instanceof StringLiteral) {
            visit((StringLiteral) node, data);
        }

        if (node instanceof Addition) {
            visit((Addition) node, data);
        }

        if (node instanceof Multiplication) {
            visit((Multiplication) node, data);
        }

        if (node instanceof Number) {
            visit((Number) node, data);
        }
    }

    public void visit(SimpleNode node, StringBuilder data) {
        visit((Node) node, data);
    }

    public void visit(Filter node, StringBuilder data) {
        data.append(node.value);
    }

    public void visit(Disjunction node, StringBuilder data) {
        visitBinaryOperation(node, "or", data);
    }

    public void visit(Conjunction node, StringBuilder data) {
        visitBinaryOperation(node, "and", data);
    }

    public void visit(Negation node, StringBuilder data) {
        data.append(" not ");
        visit(node.children[0], data);
    }

    public void visit(Comparison node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Level node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Level level = 
                Application.getLevelDao().findByName(extractName(node.value.toString()));

        data.append(level.getSchemaName()).append(".").
                append(level.getTableName()).append(".").
                append(level.getCodeProperty().getColumnName());
    }

    public void visit(Property node, StringBuilder data) {
        data.append(node.value);
    }

    public void visit(RelationalOperator node, StringBuilder data) {
        data.append(" ").append(node.value).append(" ");
    }

    public void visit(ArithmeticExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Date node, StringBuilder data) {
        data.append(node.value);
    }

    public void visit(StringLiteral node, StringBuilder data) {
        data.append(node.value);
    }

    public void visit(Addition node, StringBuilder data) {
        visitBinaryOperation(node, data);
    }

    public void visit(Multiplication node, StringBuilder data) {
        visitBinaryOperation(node, data);
    }

    public void visit(Number node, StringBuilder data) {
        data.append(node.value);
    }

    private void visitBinaryOperation(SimpleNode node, StringBuilder data) {
        visitBinaryOperation(node, node.value.toString(), data);
    }

    private void visitBinaryOperation(SimpleNode node, String op, StringBuilder data) {
        data.append("(");
        visit(node.children[0], data);
        data.append(" ").append(op).append(" ");
        visit(node.children[1], data);
        data.append(")");
    }

    private void visitChildren(SimpleNode node, StringBuilder data) {
        for (Node n : node.children) {
            visit(n, data);
        }
    }

    public void visit(FilterExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Measure node, StringBuilder data) {
        data.append(node.value);
    }

    private String extractName(String expression) {
        String patternStr = "\\b(.*)\\b";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}
