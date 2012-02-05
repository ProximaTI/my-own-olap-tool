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
public class Node {

    private String position;
    /** list of nodes */
    private ArrayList<Node> childrenNodes = new ArrayList<Node>();
    /** node value */
    private Object value = null;
    /** axis node to which this node refers */
    private SimpleNode axisNode;
    /** parent node */
    private Node parentNode;
    /** parent index */
    private int parentIndex;
    /** distance going up til root node */
    private int distanceUntilRoot = Integer.MAX_VALUE;
    /** */
    private int breadth;
    /** distance from root to deeper leaf */
    private int distanceToDeeperLeaf;

    /**
     * @return the distanceToDeeperLeaf
     */
    public int getDistanceToDeeperLeaf() {
        return distanceToDeeperLeaf;
    }

    /**
     * @param distanceToDeeperLeaf the distanceToDeeperLeaf to set
     */
    public void setDistanceToDeeperLeaf(int distanceToDeeperLeaf) {
        this.distanceToDeeperLeaf = distanceToDeeperLeaf;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public Node(SimpleNode axisNode) {
        this.axisNode = axisNode;
    }

    public Node() {
    }

    public void addChild(Node node) {
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

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * @return the childrenNodes
     */
    public ArrayList<Node> getChildrenNodes() {
        return childrenNodes;
    }

    /**
     * @param childrenNodes the childrenNodes to set
     */
    public void setChildrenNodes(ArrayList<Node> childrenNodes) {
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

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }
}
