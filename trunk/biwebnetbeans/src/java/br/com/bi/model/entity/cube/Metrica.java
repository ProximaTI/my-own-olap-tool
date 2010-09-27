/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.cube;

/**
 *
 * @author Luiz
 */
public class Metrica {

    private int id;
    private String nome;
    private String descricao;
    private Funcao funcao;
    private String coluna;
    private String expressaoFiltro;
    private boolean metricaPadrao;

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
     * @return the funcao
     */
    public Funcao getFuncao() {
        return funcao;
    }

    /**
     * @param funcao the funcao to set
     */
    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
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
     * @return the expressaoFiltro
     */
    public String getExpressaoFiltro() {
        return expressaoFiltro;
    }

    /**
     * @param expressaoFiltro the expressaoFiltro to set
     */
    public void setExpressaoFiltro(String expressaoFiltro) {
        this.expressaoFiltro = expressaoFiltro;
    }

    /**
     * @return the metricaPadrao
     */
    public boolean isMetricaPadrao() {
        return metricaPadrao;
    }

    /**
     * @param metricaPadrao the metricaPadrao to set
     */
    public void setMetricaPadrao(boolean metricaPadrao) {
        this.metricaPadrao = metricaPadrao;
    }

    enum Funcao {

        SUM, COUNT, AVG
    }
}
