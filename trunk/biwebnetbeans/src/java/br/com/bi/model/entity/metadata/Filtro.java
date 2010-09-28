/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

/**
 *
 * @author Luiz
 */
public class Filtro extends MetadataEntity {

    private String expressao;

    /**
     * @return the expressao
     */
    public String getExpressao() {
        return expressao;
    }

    /**
     * @param expressao the expressao to set
     */
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }
}
