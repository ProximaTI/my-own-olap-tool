/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Measure;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.server.olapql.language.query.ASTAxis;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTPropertyNode;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.query.AbstractQueryVisitor;
import br.com.proximati.biprime.server.olapql.language.query.translator.TranslationContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author luiz
 */
public class PivotTableModelBuilder extends AbstractQueryVisitor {

    /** model to be built */
    private PivotTableModel model = new PivotTableModel();
    /** node cache which contains nodes already referenced */
    private Map<String, PivotTableNode> pNodeCache = new HashMap<String, PivotTableNode>();
    /** Auxiliary stack which is used to determine kinship between nodes. */
    private Stack<PivotTableNode> nodeStack = new Stack<PivotTableNode>();
    /** */
    private ResultSet resultSet;

    @Override
    public void visit(ASTAxis node, Object data) throws Exception {
        if (node.jjtGetValue().equals("ROWS")) {
            nodeStack.push(model.getRowsRoot());
        } else {
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
    public PivotTableModel build(ResultSet resultSet, TranslationContext context) throws Exception {
        this.resultSet = resultSet;

        LeafNodesExtractor leafNodes = new LeafNodesExtractor();
        leafNodes.visit(context.getTranslatedSelect(), context);

        while (resultSet.next()) {
            visit(context.getTranslatedSelect(), context);

            for (Entry<SimpleNode, SimpleNode> rowLeaf : leafNodes.getRowsLeafNodesMap().entrySet()) {
                for (Entry<SimpleNode, SimpleNode> columnLeaf : leafNodes.getColumnsLeafNodesMap().entrySet()) {
                    if (isValidLeaf(rowLeaf.getKey(), context) && isValidLeaf(columnLeaf.getKey(), context)) {
                        SimpleNode measure = null;
                        if (rowLeaf.getValue() != null) {
                            measure = rowLeaf.getValue();
                        }

                        // uma métrica definida no eixo das colunas tem prevalência
                        // sobre as linhas
                        if (columnLeaf.getValue() != null) {
                            measure = columnLeaf.getValue();
                        }

                        if (measure == null) {
                            // TODO pegar a métrica padrão
                        }

                        PivotTableNode pRowNode = retrievePivotNode(rowLeaf.getKey(), context);
                        PivotTableNode pColumnNode = retrievePivotNode(columnLeaf.getKey(), context);

                        String column = context.getAxisNodePositionsMap().get(measure);
                        Object value = resultSet.getObject(column);

                        Pair<PivotTableNode, PivotTableNode> key = new Pair<PivotTableNode, PivotTableNode>(pRowNode, pColumnNode);
                        if (model.getValues().containsKey(key)) {
                            model.getValues().put(key, model.getValues().get(key).doubleValue() + ((Number) value).doubleValue());
                        } else {
                            model.getValues().put(key, (Number) value);
                        }
                    }
                }
            }
        }

        return model;
    }

    /**
     * 
     * @param node
     * @param column
     * @param value
     * @return
     */
    private PivotTableNode retrievePivotNode(SimpleNode node, TranslationContext context) throws SQLException {
        String column = context.getAxisNodePositionsMap().get(node);
        Metadata metadata = context.getQueryMetadata().getMetadataReferencedBy(node);

        Object value;
        if (metadata instanceof Measure) {
            value = metadata.getName();
        } else if (metadata instanceof Filter) {
            value = metadata.getName();
        } else {
            value = resultSet.getObject(column);
        }

        StringBuilder nodeKey = new StringBuilder(column);
        nodeKey.append("#").append(value.hashCode());

        PivotTableNode pNode = pNodeCache.get(nodeKey.toString());
        if (pNode == null) {
            pNode = new PivotTableNode(node);
            pNode.setValue(value);
            pNodeCache.put(nodeKey.toString(), pNode);
            nodeStack.peek().addChild(pNode);
        }
        return pNode;
    }

    /**
     * Dizer se uma folha é válida, consiste em dizer se algum filtro na hierarquia
     * acima da folha, para o resultset atual, está valorado como 1 (true). Se for
     * 0 (falso), esta folha não deve ser valorada no pivot model.
     *
     * @param node
     * @param context
     * @return
     */
    private boolean isValidLeaf(SimpleNode node, TranslationContext context) throws SQLException {
        boolean valid = true;

        while (node != null) {
            if (context.getQueryMetadata().getMetadataReferencedBy(
                    (SimpleNode) node) instanceof Filter) {
                break;
            }

            node = (SimpleNode) node.jjtGetParent();
        }

        if (node != null) {
            String column = context.getAxisNodePositionsMap().get(node);
            valid = resultSet.getBoolean(column);
        }

        return valid;
    }

    /**
     * Classe que tem como responsabilidade extrair os nós filhos de uma
     * árvore de consulta.
     *
     * @author luiz
     */
    class LeafNodesExtractor extends AbstractQueryVisitor {

        private Map<SimpleNode, SimpleNode> rowsLeafNodesMap = new HashMap<SimpleNode, SimpleNode>();
        private Map<SimpleNode, SimpleNode> columnsLeafNodesMap = new HashMap<SimpleNode, SimpleNode>();
        private Map<SimpleNode, SimpleNode> currentLeafNodesMap;
        private Stack<SimpleNode> nodeStack = new Stack<SimpleNode>();

        @Override
        public void visit(ASTAxis node, Object data) throws Exception {
            if (node.jjtGetValue().equals("ROWS")) {
                currentLeafNodesMap = rowsLeafNodesMap;
            } else {
                currentLeafNodesMap = columnsLeafNodesMap;
            }
            visitChildren(node, data);
        }

        @Override
        public void visit(ASTPropertyNode node, Object data) throws Exception {
            defaultVisitAction(node, (TranslationContext) data);
        }

        @Override
        public void visit(ASTLevelOrMeasureOrFilter node, Object data) throws Exception {
            defaultVisitAction(node, (TranslationContext) data);
        }

        /**
         * 
         * @return
         */
        public Map<SimpleNode, SimpleNode> getColumnsLeafNodesMap() {
            return columnsLeafNodesMap;
        }

        /**
         *
         * @return
         */
        public Map<SimpleNode, SimpleNode> getRowsLeafNodesMap() {
            return rowsLeafNodesMap;
        }

        /**
         * 
         * @param node
         * @param context
         * @throws Exception
         */
        public void defaultVisitAction(SimpleNode node, TranslationContext context) throws Exception {
            // verifica qual é a métrica mais profunda nesta hierarquia
            nodeStack.push(node);

            if (!isLeaf(node)) {
                visitChildren(node, context);
            } else {
                SimpleNode deeperMeasure = null;
                for (int i = nodeStack.size() - 1; i >= 0; i--) {
                    Metadata metadata = context.getQueryMetadata().getMetadataReferencedBy(
                            nodeStack.elementAt(i));
                    if (metadata instanceof Measure) {
                        deeperMeasure = nodeStack.elementAt(i);
                        break;
                    }
                }
                currentLeafNodesMap.put(node, deeperMeasure);
            }

            visitChildren(node, context);

            nodeStack.pop();
        }

        /**
         *
         * @param node
         * @return
         */
        public boolean isLeaf(SimpleNode node) {
            return node.jjtGetNumChildren() == 0;
        }
    }
}
