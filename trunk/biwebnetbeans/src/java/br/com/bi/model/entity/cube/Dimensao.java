/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.cube;

import java.util.List;

/**
 *
 * @author Luiz
 */
public class Dimensao {

    private int id;
    private String nome;
    private String descricao;
    private List<Nivel> niveis;

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
