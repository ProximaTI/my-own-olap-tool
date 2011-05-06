/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.measure.translator;

import br.com.proximati.biprime.server.olapql.language.measure.ASTAddition;
import br.com.proximati.biprime.server.olapql.language.measure.ASTAggregation;
import br.com.proximati.biprime.server.olapql.language.measure.ASTColumn;
import br.com.proximati.biprime.server.olapql.language.measure.ASTMeasure;
import br.com.proximati.biprime.server.olapql.language.measure.ASTMeasureExpression;
import br.com.proximati.biprime.server.olapql.language.measure.ASTMultiplication;
import br.com.proximati.biprime.server.olapql.language.measure.ASTNumber;
import br.com.proximati.biprime.server.olapql.language.measure.MeasureParserVisitor;
import br.com.proximati.biprime.server.olapql.language.measure.Node;
import br.com.proximati.biprime.server.olapql.language.measure.SimpleNode;

/**
 *
 * @author luiz
 */
public class AbstractMeasureParserVisitor implements MeasureParserVisitor {

    public void visit(Node node, StringBuilder data) throws Exception {
        if (node instanceof ASTMeasureExpression) {
            visit((ASTMeasureExpression) node, data);
        }
        if (node instanceof ASTAddition) {
            visit((ASTAddition) node, data);
        }
        if (node instanceof ASTMultiplication) {
            visit((ASTMultiplication) node, data);
        }
        if (node instanceof ASTMeasure) {
            visit((ASTMeasure) node, data);
        }
        if (node instanceof ASTAggregation) {
            visit((ASTAggregation) node, data);
        }
        if (node instanceof ASTColumn) {
            visit((ASTColumn) node, data);
        }
        if (node instanceof ASTNumber) {
            visit((ASTNumber) node, data);
        }
    }

    public void visit(SimpleNode node, StringBuilder data) throws Exception {
        visit((Node) node, data);
    }

    public void visit(ASTMeasureExpression node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    public void visit(ASTAddition node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    public void visit(ASTMultiplication node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    public void visit(ASTMeasure node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    public void visit(ASTAggregation node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    public void visit(ASTColumn node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    public void visit(ASTNumber node, StringBuilder data) throws Exception {
        visitChildren(node, data);
    }

    protected void visitChildren(SimpleNode node, StringBuilder data) throws Exception {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }
}
