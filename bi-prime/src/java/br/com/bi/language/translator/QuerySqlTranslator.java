/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.translator;

import br.com.bi.language.filter.FilterParserTreeConstants;
import br.com.bi.language.query.Addition;
import br.com.bi.language.query.ArithmeticExpression;
import br.com.bi.language.query.Axis;
import br.com.bi.language.query.Comparison;
import br.com.bi.language.query.Conjunction;
import br.com.bi.language.query.Crossjoin;
import br.com.bi.language.query.Cube;
import br.com.bi.language.query.Date;
import br.com.bi.language.query.Disjunction;
import br.com.bi.language.query.Filter;
import br.com.bi.language.query.FilterExpression;
import br.com.bi.language.query.Instruction;
import br.com.bi.language.query.LevelOrMeasure;
import br.com.bi.language.query.LevelOrMeasureOrFilter;
import br.com.bi.language.query.Measure;
import br.com.bi.language.query.Multiplication;
import br.com.bi.language.query.Negation;
import br.com.bi.language.query.Node;
import br.com.bi.language.query.Number;
import br.com.bi.language.query.Property;
import br.com.bi.language.query.QueryParserVisitor;
import br.com.bi.language.query.RelationalOperator;
import br.com.bi.language.query.Select;
import br.com.bi.language.query.SimpleNode;
import br.com.bi.language.query.StringLiteral;
import br.com.bi.model.Application;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luiz
 */
public class QuerySqlTranslator implements QueryParserVisitor {

    private br.com.bi.model.entity.metadata.Cube cube;
    private boolean translatingAxis;

    public void visit(Node node, StringBuilder data) {

        if (node instanceof Instruction) {
            visit((Instruction) node, data);
        }
        if (node instanceof Select) {
            visit((Select) node, data);
        }
        if (node instanceof Axis) {
            visit((Axis) node, data);
        }
        if (node instanceof Crossjoin) {
            visit((Crossjoin) node, data);
        }
        if (node instanceof Cube) {
            visit((Cube) node, data);
        }
        if (node instanceof FilterExpression) {
            visit((FilterExpression) node, data);
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
        if (node instanceof LevelOrMeasure) {
            visit((LevelOrMeasure) node, data);
        }
        if (node instanceof Measure) {
            visit((Measure) node, data);
        }
        if (node instanceof Filter) {
            visit((Filter) node, data);
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
        if (node instanceof LevelOrMeasureOrFilter) {
            visit((LevelOrMeasureOrFilter) node, data);
        }
    }

    public void visit(SimpleNode node, StringBuilder data) {
        visit((Node) node, data);
    }

    public void visit(Instruction node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Select node, StringBuilder data) {
        cube = Application.getCubeDao().findByName(extractName(((SimpleNode) node.jjtGetChild(2)).jjtGetValue().toString()));

        data.append("select ");
        visitChildren(node, data);
    }

    public void visit(Axis node, StringBuilder data) {
        translatingAxis = true;
        visitChildren(node, data);
        translatingAxis = false;
    }

    public void visit(Crossjoin node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Cube node, StringBuilder data) {
        data.append(" from ");
        data.append(cube.getSchemaName()).append(".").append(cube.getTableName());
    }

    public void visit(FilterExpression node, StringBuilder data) {
        data.append(" having ");
        visitChildren(node, data);
    }

    public void visit(Disjunction node, StringBuilder data) {
        visitOperation(node, "or", data);
    }

    public void visit(Conjunction node, StringBuilder data) {
        visitOperation(node, "and", data);
    }

    public void visit(Negation node, StringBuilder data) {
        if (data.charAt(data.length() - 1) != ' ') {
            data.append(" not");
        } else {
            data.append("not ");
        }

        visit(node.jjtGetChild(0), data);
    }

    public void visit(Comparison node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(LevelOrMeasure node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(extractName(node.jjtGetValue().toString()));

        if (measure != null) {
            Measure m = new Measure(FilterParserTreeConstants.JJTMEASURE);
            m.jjtSetValue(node.jjtGetValue());

            visit(m, data);
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(extractName(node.jjtGetValue().toString()));

            data.append(level.getSchemaName()).append(".").
                    append(level.getTableName()).append(".").
                    append(level.getCodeProperty().getColumnName());
        }
    }

    public void visit(Measure node, StringBuilder data) {
        MeasureSqlTranslator translator = new MeasureSqlTranslator(this.cube);

        data.append(translator.translate(node.jjtGetValue().toString()));
    }

    public void visit(Filter node, StringBuilder data) {
        FilterSqlTranslator translator = new FilterSqlTranslator(this.cube);
        String filterTranslation = translator.translate(node.jjtGetValue().toString());
        data.append(filterTranslation);
    }

    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property = level.getProperty(extractName(str[1]));

        data.append(level.getSchemaName()).append(".").
                append(level.getTableName()).append(".").
                append(property.getColumnName());
    }

    public void visit(RelationalOperator node, StringBuilder data) {
        data.append(" ").append(node.jjtGetValue()).append(" ");
    }

    public void visit(ArithmeticExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Date node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    public void visit(StringLiteral node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    public void visit(Addition node, StringBuilder data) {
        visitOperation(node, data);
    }

    public void visit(Multiplication node, StringBuilder data) {
        visitOperation(node, data);
    }

    public void visit(Number node, StringBuilder data) {
        data.append(node.jjtGetValue());
    }

    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(extractName(node.jjtGetValue().toString()));

        if (measure != null) {
            MeasureSqlTranslator translator = new MeasureSqlTranslator(cube);

            data.append(translator.translate(node.jjtGetValue().toString()));
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(extractName(node.jjtGetValue().toString()));

            if (level != null) {
                data.append(level.getSchemaName()).append(".").
                        append(level.getTableName()).append(".").
                        append(level.getCodeProperty().getColumnName());
            } else {
                br.com.bi.model.entity.metadata.Filter filter =
                        Application.getFilterDao().findByName(extractName(node.jjtGetValue().toString()));

                FilterSqlTranslator translator = new FilterSqlTranslator(cube);

                data.append("case when ");
                data.append(translator.translate(filter.getExpression()));
                data.append(" then 1 else 0 end");
            }
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
}
