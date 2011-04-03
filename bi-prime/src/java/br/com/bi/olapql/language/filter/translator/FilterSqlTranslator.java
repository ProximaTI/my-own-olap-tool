/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.filter.translator;

import br.com.bi.olapql.language.filter.Addition;
import br.com.bi.olapql.language.filter.Conjunction;
import br.com.bi.olapql.language.filter.Date;
import br.com.bi.olapql.language.filter.Disjunction;
import br.com.bi.olapql.language.filter.Filter;
import br.com.bi.olapql.language.filter.FilterParser;
import br.com.bi.olapql.language.filter.Level;
import br.com.bi.olapql.language.filter.Multiplication;
import br.com.bi.olapql.language.filter.Negation;
import br.com.bi.olapql.language.filter.Number;
import br.com.bi.olapql.language.filter.ParseException;
import br.com.bi.olapql.language.filter.Property;
import br.com.bi.olapql.language.filter.RelationalOperator;
import br.com.bi.olapql.language.filter.SimpleNode;
import br.com.bi.olapql.language.filter.StringLiteral;
import br.com.bi.olapql.language.utils.TranslationUtils;
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

        data.append(TranslationUtils.columnExpression(level.getTableName(), property.getColumnName()));
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
    public void visit(Level node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        TranslationUtils.columnExpression(level.getTableName(), level.getCodeProperty().getColumnName());
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
