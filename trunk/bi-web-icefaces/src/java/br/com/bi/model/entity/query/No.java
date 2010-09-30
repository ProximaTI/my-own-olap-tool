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

    private MetadataEntity entity;
    private List<No> filhos = new ArrayList<No>();

    public No() {
    }

    public No(MetadataEntity entity) {
        this.entity = entity;
    }

    /**
     * @return the entity
     */
    public MetadataEntity getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(MetadataEntity entity) {
        this.entity = entity;
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
}
