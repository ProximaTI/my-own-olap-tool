/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.cube;

/**
 *
 * @author Luiz
 */
public class Propriedade {

    private int id;
    private String nome;
    private String descricao;
    private String coluna;
    private boolean propriedadeCodigo;
    private boolean propriedadeNome;

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
     * @return the coluna
     */
    public String getColuna() {
        return coluna;
    }

    /**
     * @param coluna the coluna to set
     */
    public void setColuna(String coluna) {
        this.coluna = coluna;
    }

    /**
     * @return the propriedadeCodigo
     */
    public boolean isPropriedadeCodigo() {
        return propriedadeCodigo;
    }

    /**
     * @param propriedadeCodigo the propriedadeCodigo to set
     */
    public void setPropriedadeCodigo(boolean propriedadeCodigo) {
        this.propriedadeCodigo = propriedadeCodigo;
    }

    /**
     * @return the propriedadeNome
     */
    public boolean isPropriedadeNome() {
        return propriedadeNome;
    }

    /**
     * @param propriedadeNome the propriedadeNome to set
     */
    public void setPropriedadeNome(boolean propriedadeNome) {
        this.propriedadeNome = propriedadeNome;
    }
}
