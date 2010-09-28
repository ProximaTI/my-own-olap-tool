/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

import java.util.List;

/**
 *
 * @author Luiz
 */
public class Dimensao extends MetadataEntity {

    private List<Nivel> niveis;

    /**
     * @return the niveis
     */
    public List<Nivel> getNiveis() {
        return niveis;
    }

    /**
     * @param niveis the niveis to set
     */
    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }
}
