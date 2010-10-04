/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

import br.com.bi.model.entity.Entity;

/**
 *
 * @author Luiz
 */
public class CuboNivel extends Entity {

    private Nivel nivel;
    private String colunaJuncao;

    /**
     * @return the nivel
     */
    public Nivel getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the colunaJuncao
     */
    public String getColunaJuncao() {
        return colunaJuncao;
    }

    /**
     * @param colunaJuncao the colunaJuncao to set
     */
    public void setColunaJuncao(String colunaJuncao) {
        this.colunaJuncao = colunaJuncao;
    }
}
