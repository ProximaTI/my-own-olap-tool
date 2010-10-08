/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.query;

import br.com.bi.model.entity.metadata.MetadataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class Node {

    private Node parent;
    private MetadataEntity metadataEntity;
    private List<Node> children = new ArrayList<Node>();

    public Node() {
    }

    public Node(MetadataEntity entity) {
        this.metadataEntity = entity;
    }

    /**
     * @return the entity
     */
    public MetadataEntity getMetadataEntity() {
        return metadataEntity;
    }

    /**
     * @param entity the entity to set
     */
    public void setMetadataEntity(MetadataEntity entity) {
        this.metadataEntity = entity;
    }

    public void addChildren(Node child) {
        children.add(child);
        child.setParent(this);
    }

    public List<Node> getChildren() {
        return children;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isRoot() {
        return this.parent == null;
    }
}
