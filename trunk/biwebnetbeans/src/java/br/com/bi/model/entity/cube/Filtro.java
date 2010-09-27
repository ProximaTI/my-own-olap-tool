/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.cube;

/**
 *
 * @author Luiz
 */
public class Filtro {

    private int id;
    private String nome;
    private String descricao;
    private String expressao;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

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
