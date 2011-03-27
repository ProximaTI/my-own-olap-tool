/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.filter.translator;

import br.com.bi.language.filter.Addition;
import br.com.bi.language.filter.Conjunction;
import br.com.bi.language.filter.Date;
import br.com.bi.language.filter.Disjunction;
import br.com.bi.language.filter.Filter;
import br.com.bi.language.filter.FilterParser;
import br.com.bi.language.filter.FilterParserTreeConstants;
import br.com.bi.language.filter.LevelOrMeasure;
import br.com.bi.language.filter.Measure;
import br.com.bi.language.filter.Multiplication;
import br.com.bi.language.filter.Negation;
import br.com.bi.language.filter.Number;
import br.com.bi.language.filter.ParseException;
import br.com.bi.language.filter.Property;
import br.com.bi.language.filter.RelationalOperator;
import br.com.bi.language.filter.SimpleNode;
import br.com.bi.language.filter.StringLiteral;
import br.com.bi.language.measure.translator.MeasureSqlTranslator;
import br.com.bi.language.utils.TranslationUtils;
import br.com.bi.model.Application;
import br.com.bi.model.entity.metadata.Cube;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author luiz
 */
public class FilterSqlTranslator extends AbstractFilterParserVisitor {

    private Cube cube;

    public FilterSqlTranslator(Cube cube) {
        this.cube = cube;
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        try {
            br.com.bi.model.entity.metadata.Filter filter =
                    Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

            InputStream in = new ByteArrayInputStream(
                    (filter.getExpression()).getBytes());

            FilterParser parser = new FilterParser(in);

            visit(parser.filterExpression(), data);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void visit(Disjunction node, StringBuilder data) {
        visitOperation(node, "or", data);
    }

    @Override
    public void visit(Conjunction node, StringBuilder data) {
        visitOperation(node, "and", data);
    }

    @Override
    public void visit(Negation node, StringBuilder data) {
        if (data.charAt(data.length() - 1) != ' ') {
            data.append(" not");
        } else {
            data.append("not ");
        }

        visit(node.jjtGetChild(0), data);
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property = level.getProperty(TranslationUtils.extractName(str[1]));

        data.append(level.getSchemaName()).append(".").
                append(level.getTableName()).append(".").
                append(property.getColumnName());
    }

    @Override
    public void visit(RelationalOperator node, StringBuilder data) {
        data.append(" ").append(node.jjtGetValue()).append(" ");
    }

    @Override
    public void visit(Date node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(StringLiteral node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(Addition node, StringBuilder data) {
        visitOperation(node, data);
    }

    @Override
    public void visit(Multiplication node, StringBuilder data) {
        visitOperation(node, data);
    }

    @Override
    public void visit(Number node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    @Override
    public void visit(LevelOrMeasure node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (measure != null) {
            Measure m = new Measure(FilterParserTreeConstants.JJTMEASURE);
            m.jjtSetValue(node.jjtGetValue());

            visit(m, data);
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

            data.append(level.getSchemaName()).append(".").
                    append(level.getTableName()).append(".").
                    append(level.getCodeProperty().getColumnName());
        }
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

    @Override
    public void visit(Measure node, StringBuilder data) {
        MeasureSqlTranslator translator = new MeasureSqlTranslator(this.cube);

        data.append(translator.translate(node.jjtGetValue().toString()));
    }

    public String translate(String expression) {
        InputStream in = new ByteArrayInputStream(
                (expression).getBytes());

        FilterParser parser = new FilterParser(in);

        try {
            SimpleNode node = parser.filterExpression();

            StringBuilder sb = new StringBuilder();

            FilterSqlTranslator translator = new FilterSqlTranslator(this.cube);
            translator.visit(node, sb);

            return sb.toString();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
