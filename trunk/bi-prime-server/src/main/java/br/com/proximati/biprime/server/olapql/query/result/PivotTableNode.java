/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import java.util.ArrayList;

/**
 *
 * @author luiz
 */
public class PivotTableNode {

    protected static final int WHITE = 0;
    protected static final int GRAY = 1;
    protected static final int BLACK = 2;
    
    /** list of nodes */
    private ArrayList<PivotTableNode> childrenNodes = new ArrayList<PivotTableNode>();
    /** node value */
    private Object value = null;
    /** axis node to which this node refers */
    private SimpleNode axisNode;
    /** parent node */
    private PivotTableNode parentNode;
    /** parent index */
    private int parentIndex;
    /** distance going up til root node */
    private int distanceUntilRoot = Integer.MAX_VALUE;
    /** */
    private int breadth;


    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public PivotTableNode(SimpleNode axisNode) {
        this.axisNode = axisNode;
    }

    public PivotTableNode() {
    }

    public void addChild(PivotTableNode node) {
        node.setParentIndex(childrenNodes.size());
        node.setParentNode(this);
        childrenNodes.add(node);
    }

    public SimpleNode getAxisNode() {
        return axisNode;
    }

    public void setAxisNode(SimpleNode axisNode) {
        this.axisNode = axisNode;
    }

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
     * @return the distanceUntilRoot
     */
    public int getDistanceUntilRoot() {
        return distanceUntilRoot;
    }

    /**
     * @param distanceUntilRoot the distanceUntilRoot to set
     */
    public void setDistanceUntilRoot(int distanceUntilRoot) {
        this.distanceUntilRoot = distanceUntilRoot;
    }
    /**
     * @return the breadth
     */
    public int getBreadth() {
        return breadth;
    }

    /**
     * @param breadth the breadth to set
     */
    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

}
