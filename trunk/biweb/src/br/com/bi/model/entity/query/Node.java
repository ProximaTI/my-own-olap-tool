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

    private Node pai;
    private MetadataEntity metadataEntity;
    private List<Node> filhos = new ArrayList<Node>();

    public Node() {
    }

    public Node(Node pai, MetadataEntity entity) {
        this.pai = pai;
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

    /**
     * @return the filhos
     */
    public List<Node> getChildren() {
        return filhos;
    }

    /**
     * @param filhos the filhos to set
     */
    public void setFilhos(List<Node> filhos) {
        this.filhos = filhos;
    }

    /**
     * @return the pai
     */
    public Node getParent() {
        return pai;
    }

    /**
     * @param pai the pai to set
     */
    public void setPai(Node pai) {
        this.pai = pai;
    }

    public boolean isRoot() {
        return this.pai == null;
    }
}
