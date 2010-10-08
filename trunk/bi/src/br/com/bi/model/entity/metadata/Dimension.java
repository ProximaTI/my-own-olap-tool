/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class Dimension extends MetadataEntity {

    private List<Level> niveis = new ArrayList<Level>();

    /**
     * @return the niveis
     */
    public List<Level> getLevels() {
        return niveis;
    }

    /**
     * @param niveis the niveis to set
     */
    public void setNiveis(List<Level> niveis) {
        this.niveis = niveis;
    }
}
