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
public class No {

    private No pai;
    private MetadataEntity metadataEntity;
    private List<No> filhos = new ArrayList<No>();

    public No() {
    }

    public No(No pai, MetadataEntity entity) {
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
    public List<No> getFilhos() {
        return filhos;
    }

    /**
     * @param filhos the filhos to set
     */
    public void setFilhos(List<No> filhos) {
        this.filhos = filhos;
    }

    /**
     * @return the pai
     */
    public No getPai() {
        return pai;
    }

    /**
     * @param pai the pai to set
     */
    public void setPai(No pai) {
        this.pai = pai;
    }

    public boolean isRaiz() {
        return this.pai == null;
    }
}
