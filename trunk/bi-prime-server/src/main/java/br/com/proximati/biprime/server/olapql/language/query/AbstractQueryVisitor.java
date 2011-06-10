/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query;

/**
 *
 * @author luiz
 */
public abstract class AbstractQueryVisitor implements QueryParserVisitor {

    public void visit(Node node, Object data) throws Exception {
        if (node instanceof ASTSelect)
            visit((ASTSelect) node, data);
        if (node instanceof ASTDetachedFilterExpression)
            visit((ASTDetachedFilterExpression) node, data);
        if (node instanceof ASTFilterExpression)
            visit((ASTFilterExpression) node, data);
        if (node instanceof ASTAxis)
            visit((ASTAxis) node, data);
        if (node instanceof ASTLevelOrMeasureOrFilter)
            visit((ASTLevelOrMeasureOrFilter) node, data);
        if (node instanceof ASTPropertyNode)
            visit((ASTPropertyNode) node, data);
        if (node instanceof ASTLevel)
            visit((ASTLevel) node, data);
        if (node instanceof ASTFilter)
            visit((ASTFilter) node, data);
        if (node instanceof ASTProperty)
            visit((ASTProperty) node, data);
        if (node instanceof ASTCube)
            visit((ASTCube) node, data);
        if (node instanceof ASTOrCondition)
            visit((ASTOrCondition) node, data);
        if (node instanceof ASTAndCondition)
            visit((ASTAndCondition) node, data);
        if (node instanceof ASTCondition)
            visit((ASTCondition) node, data);
        if (node instanceof ASTNegation)
            visit((ASTNegation) node, data);
        if (node instanceof ASTInExpression)
            visit((ASTInExpression) node, data);
        if (node instanceof ASTLikeExpression)
            visit((ASTLikeExpression) node, data);
        if (node instanceof ASTStartsWithExpression)
            visit((ASTStartsWithExpression) node, data);
        if (node instanceof ASTEndsWithExpression)
            visit((ASTEndsWithExpression) node, data);
        if (node instanceof ASTCompare)
            visit((ASTCompare) node, data);
        if (node instanceof ASTAdditiveExpression)
            visit((ASTAdditiveExpression) node, data);
        if (node instanceof ASTMultiplicativeExpression)
            visit((ASTMultiplicativeExpression) node, data);
        if (node instanceof ASTNumberLiteral)
            visit((ASTNumberLiteral) node, data);
        if (node instanceof ASTDateLiteral)
            visit((ASTDateLiteral) node, data);
        if (node instanceof ASTStringLiteral)
            visit((ASTStringLiteral) node, data);
    }

    @Override
    public void visit(SimpleNode node, Object data) throws Exception {
        visit((Node) node, data);
    }

    @Override
    public void visit(ASTSelect node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTAxis node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTLevelOrMeasureOrFilter node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTProperty node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTPropertyNode node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTCube node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTFilterExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTNegation node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTLevel node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTFilter node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTStringLiteral node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTOrCondition node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTAndCondition node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTCondition node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTInExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTLikeExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTStartsWithExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTEndsWithExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTCompare node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTAdditiveExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTMultiplicativeExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTNumberLiteral node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTDateLiteral node, Object data) throws Exception {
        visitChildren(node, data);
    }

    @Override
    public void visit(ASTDetachedFilterExpression node, Object data) throws Exception {
        visitChildren(node, data);
    }

    protected Integer childIndex(Node node) {
        for (int i = 0; i < node.jjtGetParent().jjtGetNumChildren(); i++)
            if (node.jjtGetParent().jjtGetChild(i).equals(node))
                return i;
        return -1;
    }

    protected void visitChildren(SimpleNode node, Object data) throws Exception {
        for (int i = 0; i < node.jjtGetNumChildren(); i++)
            visit(node.jjtGetChild(i), data);
    }
}
