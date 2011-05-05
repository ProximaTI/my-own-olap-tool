/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import br.com.proximati.biprime.server.olapql.language.query.Node;
import java.util.ArrayList;

/**
 *
 * @author luiz
 */
public class PivotTableNode {

    /** list of nodes */
    private ArrayList<PivotTableNode> childrenNodes = new ArrayList<PivotTableNode>();
    /** flag used to define if current node is expanded */
    private boolean nodeExpanded = false;
    /** node value */
    private Object value = null;
    /** depth level */
    private int level = 0;
    /** axis node to which this node refers */
    private Node axisNode;

    public PivotTableNode(Node axisNode) {
        this.axisNode = axisNode;
    }

    public PivotTableNode() {
    }

    public void addChild(PivotTableNode node) {
        this.getChildrenNodes().add(node);
        node.setParentNode(this);
    }

    public Node getAxisNode() {
        return axisNode;
    }

    public void setAxisNode(Node axisNode) {
        this.axisNode = axisNode;
    }
    /** parent node */
    private PivotTableNode parentNode;

    public PivotTableNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(PivotTableNode parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * @return the childrenNodes
     */
    public ArrayList<PivotTableNode> getChildrenNodes() {
        return childrenNodes;
    }

    /**
     * @param childrenNodes the childrenNodes to set
     */
    public void setChildrenNodes(ArrayList<PivotTableNode> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    /**
     * @return the nodeExpanded
     */
    public boolean isNodeExpanded() {
        return nodeExpanded;
    }

    /**
     * @param nodeExpanded the nodeExpanded to set
     */
    public void setNodeExpanded(boolean nodeExpanded) {
        this.nodeExpanded = nodeExpanded;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
