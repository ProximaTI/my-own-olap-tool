/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import br.com.proximati.biprime.util.TraversingListener;
import br.com.proximati.biprime.util.BreadthFirstSearch;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Measure;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.util.Pair;
import br.com.proximati.biprime.server.olapql.language.query.ASTAxis;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTPropertyNode;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.query.AbstractQueryVisitor;
import br.com.proximati.biprime.server.olapql.language.query.translator.TranslationContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author luiz
 */
public class QueryResultBuilder extends AbstractQueryVisitor {

    /** model to be built */
    private QueryResultModel model = new QueryResultModel();
    /** node cache which contains nodes already referenced */
    private Map<String, Node> pNodeCache = new HashMap<String, Node>();
    /** Auxiliary stack which is used to determine kinship between nodes. */
    private Stack<Node> nodeStack = new Stack<Node>();
    /** */
    private ResultSet resultSet;
    /** lista com os nós que são as folhas */
    private List<Node> rowLeafs = new ArrayList<Node>();
    private List<Node> columnLeafs = new ArrayList<Node>();
    /** Mapeia cada nó folha à métrica que ele detalha */
    private Map<SimpleNode, SimpleNode> rowMeasuresMap = new HashMap<SimpleNode, SimpleNode>();
    private Map<SimpleNode, SimpleNode> columnMeasuresMap = new HashMap<SimpleNode, SimpleNode>();
    private ASTLevelOrMeasureOrFilter lastMeasureSeen;

    @Override
    public void visit(ASTAxis node, Object data) throws Exception {
        if (node.jjtGetValue().equals("ROWS")) {
            model.getRowsRoot().setValue("ROWS");
            nodeStack.push(model.getRowsRoot());
        } else {
            model.getColumnsRoot().setValue("COLUMNS");
            nodeStack.push(model.getColumnsRoot());
        }
        super.visit(node, data);
        nodeStack.pop();
    }

    @Override
    public void visit(ASTPropertyNode node, Object data) throws Exception {
        nodeStack.push(retrievePivotNode(node, (TranslationContext) data));
        visitChildren(node, data);
        nodeStack.pop();
    }

    @Override
    public void visit(ASTLevelOrMeasureOrFilter node, Object data) throws Exception {
        nodeStack.push(retrievePivotNode(node, (TranslationContext) data));
        visitChildren(node, data);
        nodeStack.pop();
    }

    /**
     *
     * @param resultSet
     * @param context
     * @return
     * @throws Exception
     */
    public QueryResultModel build(ResultSet resultSet, TranslationContext context) throws Exception {
        this.resultSet = resultSet;

        while (resultSet.next()) {
            rowLeafs.clear();
            columnLeafs.clear();

            visit(context.getTranslatedSelect(), context);

            for (Node rowLeaf : rowLeafs)
                for (Node columnLeaf : columnLeafs)
                    if (isValidLeaf(rowLeaf.getAxisNode(), context)
                            && isValidLeaf(columnLeaf.getAxisNode(), context)) {
                        SimpleNode measure = rowMeasuresMap.get(rowLeaf.getAxisNode());

                        // uma métrica definida no eixo das colunas tem prevalência
                        // sobre as linhas
                        if (columnMeasuresMap.get(columnLeaf.getAxisNode()) != null)
                            measure = columnMeasuresMap.get(columnLeaf.getAxisNode());

                        if (measure == null) {
                            // TODO pegar a métrica padrão
                        }

                        String column = context.getAxisNodePositionsMap().get(measure);
                        Object value = resultSet.getObject(column);

                        Pair<Node, Node> key =
                                new Pair<Node, Node>(rowLeaf, columnLeaf);
                        if (model.getValues().containsKey(key))
                            model.getValues().put(key,
                                                  model.getValues().get(key).doubleValue() + ((Number) value).doubleValue());
                        else
                            model.getValues().put(key, (Number) value);
                    }
        }

        calcNodeAttributes(model.getColumnsRoot());
        calcNodeAttributes(model.getRowsRoot());

        return model;
    }

    /**
     * Calcula a altura da árvore de nós e a distância de cada nó
     * até a raiz. A altura é dada pela distância até a raiz do nó mais
     * profundo. O cálculo é feito através de caminhamento
     * BFS (Breadth-First Search).
     * @param root
     */
    private void calcNodeAttributes(Node root) {
        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {

            public void visitingRoot(Node s) {
                s.setDistanceUntilRoot(0);
            }

            public void visitingLeaf(Node s, Node u) {
                u.setBreadth(1);
                u.setDistanceUntilRoot(u.getParentNode().getDistanceUntilRoot() + 1);

                int distanceToDeeperLeaf = 0;

                Node r = u.getParentNode();
                while (r != null) {
                    r.setBreadth(r.getBreadth() + 1);
                    r.setDistanceToDeeperLeaf(Math.max(r.getDistanceToDeeperLeaf(), ++distanceToDeeperLeaf));
                    r = r.getParentNode();
                }
            }

            public void visitingNonLeaf(Node s, Node v) {
                if (v.getParentNode() != null)
                    v.setDistanceUntilRoot(v.getParentNode().getDistanceUntilRoot() + 1);
            }
        });

        bfs.perform(root);
    }

    /**
     * 
     * @param node
     * @param column
     * @param value
     * @return
     */
    private Node retrievePivotNode(SimpleNode node, TranslationContext context) throws SQLException {
        String column = context.getAxisNodePositionsMap().get(node);
        Metadata metadata = context.getQueryMetadata().getMetadataReferencedBy(node);

        Object value;
        if (metadata instanceof Measure) {
            lastMeasureSeen = (ASTLevelOrMeasureOrFilter) node;
            value = metadata.getName();
        } else if (metadata instanceof Filter)
            value = metadata.getName();
        else
            value = resultSet.getObject(column);

        StringBuilder nodeKey = new StringBuilder("{v=");
        nodeKey.append(value).append(",n=").append(node.jjtGetValue()).
                append(",c=").append(column).append("}");

        StringBuilder fullKey = new StringBuilder();
        if (!nodeStack.isEmpty())
            for (Node n : nodeStack)
                fullKey.append(n.toString());

        fullKey.append(nodeKey);

        Node pNode = pNodeCache.get(fullKey.toString());
        if (pNode == null) {
            pNode = new Node(node);
            pNode.setValue(value);
            pNodeCache.put(fullKey.toString(), pNode);
            nodeStack.peek().addChild(pNode);
        }

        if (isLeaf(node))
            if (nodeStack.get(0).getValue().equals("ROWS")) {
                rowLeafs.add(pNode);
                rowMeasuresMap.put(node, lastMeasureSeen);
            } else {
                columnLeafs.add(pNode);
                columnMeasuresMap.put(node, lastMeasureSeen);
            }

        return pNode;
    }

    /**
     *
     * @param node
     * @return
     */
    public boolean isLeaf(SimpleNode node) {
        return node.jjtGetNumChildren() == 0;
    }

    /**
     * Dizer se uma folha é válida, consiste em dizer se algum filtro na hierarquia
     * acima da folha, para o resultset atual, está valorado como 1 (true). Se for
     * 0 (falso), esta folha não deve ser valorada no model.
     *
     * @param node
     * @param context
     * @return
     */
    private boolean isValidLeaf(SimpleNode node, TranslationContext context) throws SQLException {
        boolean valid = true;

        while (node != null) {
            if (context.getQueryMetadata().getMetadataReferencedBy(
                    (SimpleNode) node) instanceof Filter)
                break;

            node = (SimpleNode) node.jjtGetParent();
        }

        if (node != null) {
            String column = context.getAxisNodePositionsMap().get(node);
            valid = resultSet.getBoolean(column);
        }

        return valid;
    }
}
