/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.translator;

import br.com.bi.language.filter.FilterParser;
import br.com.bi.language.measure.Addition;
import br.com.bi.language.measure.Aggregation;
import br.com.bi.language.measure.Column;
import br.com.bi.language.measure.Measure;
import br.com.bi.language.measure.MeasureExpression;
import br.com.bi.language.measure.MeasureParser;
import br.com.bi.language.measure.MeasureParserVisitor;
import br.com.bi.language.measure.Multiplication;
import br.com.bi.language.measure.Node;
import br.com.bi.language.measure.Number;
import br.com.bi.language.measure.ParseException;
import br.com.bi.language.measure.SimpleNode;
import br.com.bi.model.Application;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luiz
 */
public class MeasureSqlTranslator implements MeasureParserVisitor {

    private Stack<String> filterStack = new Stack<String>();

    public static void main(String args[]) {
        String expression = "média(\"store_sales\") + [Total] + [Total (Recursos Humanos)]";

        MeasureSqlTranslator translator = new MeasureSqlTranslator();

        String translation = translator.translate(expression);
        
        System.out.print("expression: ");
        System.out.println(expression);

        System.out.print("translation: ");
        System.out.println(translation);
    }

    public void visit(Node node, StringBuilder data) {
        if (node instanceof MeasureExpression) {
            visit((MeasureExpression) node, data);
        }
        if (node instanceof Addition) {
            visit((Addition) node, data);
        }
        if (node instanceof Multiplication) {
            visit((Multiplication) node, data);
        }
        if (node instanceof Measure) {
            visit((Measure) node, data);
        }
        if (node instanceof Aggregation) {
            visit((Aggregation) node, data);
        }
        if (node instanceof Column) {
            visit((Column) node, data);
        }
        if (node instanceof Number) {
            visit((Number) node, data);
        }
    }

    public void visit(SimpleNode node, StringBuilder data) {
        visit((Node) node, data);
    }

    public void visit(MeasureExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(br.com.bi.language.measure.Addition node, StringBuilder data) {
        visitOperation(node, data);
    }

    public void visit(br.com.bi.language.measure.Multiplication node, StringBuilder data) {
        visitOperation(node, data);
    }

    public void visit(br.com.bi.language.measure.Measure node, StringBuilder data) {
        try {
            br.com.bi.model.entity.metadata.Measure measure =
                    Application.getMeasureDao().findByName(extractName(node.jjtGetValue().toString()));

            if (measure.getFilterExpression() != null) {
                filterStack.push(measure.getFilterExpression());
            }

            InputStream in = new ByteArrayInputStream(
                    (measure.getExpression()).getBytes());

            MeasureParser parser = new MeasureParser(in);

            visit(parser.measureExpression(), data);

            if (measure.getFilterExpression() != null) {
                filterStack.pop();
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void visit(Aggregation node, StringBuilder data) {
        if (node.jjtGetValue().equals("quantidade")) {
            data.append("count(");
        }

        if (node.jjtGetValue().equals("média")) {
            data.append("avg(");
        }

        if (node.jjtGetValue().equals("máximo")) {
            data.append("max(");
        }
        if (node.jjtGetValue().equals("mínimo")) {
            data.append("min(");
        }
        if (node.jjtGetValue().equals("soma")) {
            data.append("sum(");
        }

        if (!filterStack.empty()) {
            data.append("case when (");
            translateFilter(filterStack.peek(), data);
            data.append(" then ");
            visitChildren(node, data);
            data.append(" else null end");
        } else {
            visitChildren(node, data);
        }

        data.append(")");
    }

    public void visit(Column node, StringBuilder data) {
        // TODO
        data.append(node.jjtGetValue());
    }

    public void visit(br.com.bi.language.measure.Number node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    private void visitOperation(SimpleNode node, StringBuilder data) {
        visitOperation(node, node.jjtGetValue().toString(), data);
    }

    private void visitOperation(SimpleNode node, String op, StringBuilder data) {
        data.append("(");

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
            if (i < node.jjtGetNumChildren() - 1) {
                data.append(" ").append(op).append(" ");
            }
        }

        data.append(")");
    }

    private void visitChildren(SimpleNode node, StringBuilder data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }

    private String extractName(String expression) {
        String patternStr = "\\[(.*)\\]";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void translateFilter(String filterExpression, StringBuilder data) {
        try {
            InputStream in = new ByteArrayInputStream(
                    (filterExpression).getBytes());

            FilterParser parser = new FilterParser(in);

            FilterSqlTranslator translator = new FilterSqlTranslator();

            translator.visit(parser.filterExpression(), data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String translate(String expression) {
        InputStream in = new ByteArrayInputStream(
                (expression).getBytes());

        MeasureParser parser = new MeasureParser(in);

        try {
            SimpleNode node = parser.measureExpression();

            StringBuilder sb = new StringBuilder();

            MeasureSqlTranslator translator = new MeasureSqlTranslator();
            translator.visit(node, sb);

            return sb.toString();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
