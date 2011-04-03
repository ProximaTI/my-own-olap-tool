/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.query.translator;

import br.com.bi.language.query.Addition;
import br.com.bi.language.query.ArithmeticExpression;
import br.com.bi.language.query.Axis;
import br.com.bi.language.query.Comparison;
import br.com.bi.language.query.Conjunction;
import br.com.bi.language.query.Cube;
import br.com.bi.language.query.Date;
import br.com.bi.language.query.Disjunction;
import br.com.bi.language.query.Filter;
import br.com.bi.language.query.FilterExpression;
import br.com.bi.language.query.Instruction;
import br.com.bi.language.query.Level;
import br.com.bi.language.query.LevelOrMeasureOrFilter;
import br.com.bi.language.query.Multiplication;
import br.com.bi.language.query.Negation;
import br.com.bi.language.query.Node;
import br.com.bi.language.query.Number;
import br.com.bi.language.query.Property;
import br.com.bi.language.query.PropertyNode;
import br.com.bi.language.query.QueryParserVisitor;
import br.com.bi.language.query.RelationalOperator;
import br.com.bi.language.query.Select;
import br.com.bi.language.query.SimpleNode;
import br.com.bi.language.query.StringLiteral;

/**
 *
 * @author luiz
 */
public class AbstractQueryVisitor implements QueryParserVisitor {

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
        if (node instanceof Level) {
            visit((Level) node, data);
        }
        if (node instanceof Filter) {
            visit((Filter) node, data);
        }
        if (node instanceof Property) {
            visit((Property) node, data);
        }
        if (node instanceof PropertyNode) {
            visit((PropertyNode) node, data);
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

    @Override
    public void visit(SimpleNode node, StringBuilder data) {
        visit((Node) node, data);
    }

    @Override
    public void visit(Instruction node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Select node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Axis node, StringBuilder data) {
        visitChildren(node, data);
    }
    @Override
    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(PropertyNode node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Cube node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(FilterExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Disjunction node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Conjunction node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Negation node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Comparison node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Level node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(RelationalOperator node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(ArithmeticExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Date node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(StringLiteral node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Addition node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Multiplication node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Number node, StringBuilder data) {
        visitChildren(node, data);
    }

    protected void visitChildren(SimpleNode node, StringBuilder data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }

    protected Integer childIndex(Node node) {
        for (int i = 0; i < node.jjtGetParent().jjtGetNumChildren(); i++) {
            if (node.jjtGetParent().jjtGetChild(i).equals(node)) {
                return i;
            }
        }
        return -1;
    }
}
