/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.measure.translator;

import br.com.bi.language.measure.Addition;
import br.com.bi.language.measure.Aggregation;
import br.com.bi.language.measure.Column;
import br.com.bi.language.measure.Measure;
import br.com.bi.language.measure.MeasureExpression;
import br.com.bi.language.measure.MeasureParserVisitor;
import br.com.bi.language.measure.Multiplication;
import br.com.bi.language.measure.Node;
import br.com.bi.language.measure.Number;
import br.com.bi.language.measure.SimpleNode;

/**
 *
 * @author luiz
 */
public class AbstractMeasureParserVisitor implements MeasureParserVisitor {

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

    public void visit(Addition node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Multiplication node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Measure node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Aggregation node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Column node, StringBuilder data) {
        visitChildren(node, data);
    }

    public void visit(Number node, StringBuilder data) {
        visitChildren(node, data);
    }

    protected void visitChildren(SimpleNode node, StringBuilder data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }
}
