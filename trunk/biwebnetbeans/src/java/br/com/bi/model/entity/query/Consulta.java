/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.query;

import br.com.bi.model.entity.metadata.Cubo;

/**
 *
 * @author Luiz
 */
public class Consulta {

    private Cubo cubo;
    private No linha = new No();
    private No coluna = new No();

    public Consulta(Cubo cubo) {
        this.cubo = cubo;
    }

    /**
     * @return the cubo
     */
    public Cubo getCubo() {
        return cubo;
    }

    /**
     * @param cubo the cubo to set
     */
    public void setCubo(Cubo cubo) {
        this.cubo = cubo;
    }

    /**
     * @return the linha
     */
    public No getLinha() {
        return linha;
    }

    /**
     * @param linha the linha to set
     */
    public void setLinha(No linha) {
        this.linha = linha;
    }

    /**
     * @return the coluna
     */
    public No getColuna() {
        return coluna;
    }

    /**
     * @param coluna the coluna to set
     */
    public void setColuna(No coluna) {
        this.coluna = coluna;
    }
}
