/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.filter.translator;

import br.com.bi.language.filter.Addition;
import br.com.bi.language.filter.ArithmeticExpression;
import br.com.bi.language.filter.Comparison;
import br.com.bi.language.filter.Conjunction;
import br.com.bi.language.filter.Date;
import br.com.bi.language.filter.Disjunction;
import br.com.bi.language.filter.Filter;
import br.com.bi.language.filter.FilterExpression;
import br.com.bi.language.filter.FilterParserVisitor;
import br.com.bi.language.filter.LevelOrMeasure;
import br.com.bi.language.filter.Measure;
import br.com.bi.language.filter.Multiplication;
import br.com.bi.language.filter.Negation;
import br.com.bi.language.filter.Node;
import br.com.bi.language.filter.Number;
import br.com.bi.language.filter.Property;
import br.com.bi.language.filter.RelationalOperator;
import br.com.bi.language.filter.SimpleNode;
import br.com.bi.language.filter.StringLiteral;

/**
 *
 * @author luiz
 */
public abstract class AbstractFilterParserVisitor implements FilterParserVisitor {

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

        if (node instanceof LevelOrMeasure) {
            visit((LevelOrMeasure) node, data);
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

    public void visit(FilterExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Disjunction node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Conjunction node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Negation node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Comparison node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Measure node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(LevelOrMeasure node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Filter node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Property node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(RelationalOperator node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(ArithmeticExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Date node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(StringLiteral node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Addition node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Multiplication node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Number node, StringBuilder data) {
        visitChildren(node, data);
    }

    private void visitChildren(SimpleNode node, StringBuilder data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }
}
