/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.measure.translator;

import br.com.proximati.biprime.server.olapql.language.query.translator.TranslationUtils;
import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.metadata.entity.Cube;
import br.com.proximati.biprime.metadata.entity.Measure;
import br.com.proximati.biprime.server.olapql.language.measure.ASTAddition;
import br.com.proximati.biprime.server.olapql.language.measure.ASTAggregation;
import br.com.proximati.biprime.server.olapql.language.measure.ASTColumn;
import br.com.proximati.biprime.server.olapql.language.measure.ASTMeasure;
import br.com.proximati.biprime.server.olapql.language.measure.ASTMultiplication;
import br.com.proximati.biprime.server.olapql.language.measure.ASTNumber;
import br.com.proximati.biprime.server.olapql.language.measure.MeasureParser;
import br.com.proximati.biprime.server.olapql.language.measure.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilterExpression;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.query.translator.QuerySqlTranslator;
import br.com.proximati.biprime.server.olapql.language.query.translator.TranslationContext;
import java.util.Stack;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author luiz
 */
public class MeasureSqlTranslator extends AbstractMeasureParserVisitor {

    private Stack<String> filterStack = new Stack<String>();
    private Cube cube;

    public MeasureSqlTranslator(Cube cube) {
        this.cube = cube;
    }

    @Override
    public void visit(ASTAddition node, StringBuilder data) throws Exception {
        visitOperation(node, data);
    }

    @Override
    public void visit(ASTMultiplication node, StringBuilder data) throws Exception {
        visitOperation(node, data);
    }

    @Override
    public void visit(ASTMeasure node, StringBuilder data) throws Exception {
        Measure measure = Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (StringUtils.isNotBlank(measure.getFilterExpression())) {
            filterStack.push(measure.getFilterExpression());
        }

        MeasureParser parser = new MeasureParser(IOUtils.toInputStream(measure.getExpression()));
        visit(parser.measureExpression(), data);

        if (StringUtils.isNotBlank(measure.getFilterExpression())) {
            filterStack.pop();
        }
    }

    @Override
    public void visit(ASTAggregation node, StringBuilder data) throws Exception {
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

            QueryParser parser = new QueryParser(IOUtils.toInputStream(filterStack.peek()));
            QuerySqlTranslator translator = new QuerySqlTranslator();
            TranslationContext context = translator.translate((ASTFilterExpression) parser.detachedFilterExpression());
            data.append(context.getOutput());

            data.append(") then ");
            visitChildren(node, data);
            data.append(" else null end");
        } else {
            visitChildren(node, data);
        }

        data.append(")");
    }

    @Override
    public void visit(ASTColumn node, StringBuilder data) throws Exception {
        data.append(cube.getTableName()).append(".").append(TranslationUtils.extractColumn(node.jjtGetValue().toString()));
    }

    @Override
    public void visit(ASTNumber node, StringBuilder data) throws Exception {
        data.append(node.jjtGetValue());
    }

    private void visitOperation(SimpleNode node, StringBuilder data) throws Exception {
        visitOperation(node, node.jjtGetValue().toString(), data);
    }

    private void visitOperation(SimpleNode node, String op, StringBuilder data) throws Exception {
        data.append("(");

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
            if (i < node.jjtGetNumChildren() - 1) {
                data.append(" ").append(op).append(" ");
            }
        }

        data.append(")");
    }

    public String translate(String expression) throws Exception {
        MeasureParser parser = new MeasureParser(IOUtils.toInputStream(expression));
        SimpleNode node = parser.measureExpression();
        StringBuilder sb = new StringBuilder();
        MeasureSqlTranslator translator = new MeasureSqlTranslator(this.cube);
        translator.visit(node, sb);

        return sb.toString();
    }
}
