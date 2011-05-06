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
import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.Node;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.query.translator.AbstractQueryTranslator;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.apache.commons.collections15.BidiMap;

/**
 *
 * @author luiz
 */
public class PivotTableModelBuilder extends AbstractQueryTranslator {

    /** model to be built */
    private PivotTableModel model = new PivotTableModel();
    /** node cache which contains nodes already referenced */
    private Map<String, PivotTableNode> pNodeCache = new HashMap<String, PivotTableNode>();
    /** Auxiliary stack which is used to determine kinship between nodes. */
    private Stack<PivotTableNode> nodeStack = new Stack<PivotTableNode>();
    /** map that contains node coordinates */
    private BidiMap<Node, String> axisNodeCoordinateMap;
    /** resultset which is the data source in order to build the pivot model */
    private ResultSet resultSet;
    private Map<Node, Metadata> axisNodeMetadataMap;

    public PivotTableModel build(ASTSelect select, ResultSet resultSet,
            BidiMap<Node, String> axisNodeCoordinateMap,
            Map<Node, Metadata> axisNodeMetadataMap) throws Exception {
        this.resultSet = resultSet;
        this.axisNodeCoordinateMap = axisNodeCoordinateMap;
        this.axisNodeMetadataMap = axisNodeMetadataMap;

        while (resultSet.next()) {
            visit(select, null);
        }

        return model;
    }

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
        String column = axisNodeCoordinateMap.get(node);
        Object value = resultSet.getObject(column);
        nodeStack.push(retrieveNode(node, column, value));
        visitChildren(node, null);
        nodeStack.pop();
    }

    @Override
    public void visit(ASTLevelOrMeasureOrFilter node, Object data) throws Exception {
        Metadata metadata = axisNodeMetadataMap.get(node);
        String column = axisNodeCoordinateMap.get(node);

        Object value;
        if (metadata instanceof Measure) {
            value = metadata.getName();
        } else if (metadata instanceof Filter) {
            value = metadata.getName();
        } else {
            value = resultSet.getObject(column);
        }

        nodeStack.push(retrieveNode(node, column, value));
        visitChildren(node, null);
        nodeStack.pop();
    }

    private PivotTableNode retrieveNode(SimpleNode relatedAxisNode, String column, Object value) {
        StringBuilder nodeKey = new StringBuilder(column);
        nodeKey.append("#").append(value.hashCode());

        PivotTableNode node = pNodeCache.get(nodeKey.toString());
        if (node == null) {
            node = new PivotTableNode(relatedAxisNode);
            node.setValue(value);
            pNodeCache.put(nodeKey.toString(), node);
            nodeStack.peek().addChild(node);
        }
        return node;
    }
}
