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
public class Cubo {

    private int id;
    private String nome;
    private String descricao;
    private List<Dimensao> dimensoes;
    private List<Metrica> metricas;
    private List<Filtro> filtros;

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
     * @return the dimensoes
     */
    public List<Dimensao> getDimensoes() {
        return dimensoes;
    }

    /**
     * @param dimensoes the dimensoes to set
     */
    public void setDimensoes(List<Dimensao> dimensoes) {
        this.dimensoes = dimensoes;
    }

    /**
     * @return the filtros
     */
    public List<Filtro> getFiltros() {
        return filtros;
    }

    /**
     * @param filtros the filtros to set
     */
    public void setFiltros(List<Filtro> filtros) {
        this.filtros = filtros;
    }

    /**
     * @return the metricas
     */
    public List<Metrica> getMetricas() {
        return metricas;
    }

    /**
     * @param metricas the metricas to set
     */
    public void setMetricas(List<Metrica> metricas) {
        this.metricas = metricas;
    }
}
