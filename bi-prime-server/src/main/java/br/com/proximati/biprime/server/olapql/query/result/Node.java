/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import br.com.proximati.biprime.metadata.entity.Metadata;
import java.util.ArrayList;

/**
 *
 * @author luiz
 */
public class Node {

    /** list of nodes */
    private ArrayList<Node> childrenNodes = new ArrayList<Node>();
    /** flag used to define if current node is expanded */
    private boolean nodeExpanded = false;
    /** node value */
    private Object value = null;
    /** this is a root node */
    private boolean rootNode = false;
    /** depth level */
    private int level = 0;
    /** metadata to which this node refers */
    private Metadata metadata;

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
     * @return the rootNode
     */
    public boolean isRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(boolean rootNode) {
        this.rootNode = rootNode;
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

    /**
     * @return the metadata
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
