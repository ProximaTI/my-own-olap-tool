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
public class Cube extends MetadataEntity {

    private String esquema;
    private String tabela;
    private List<CuboNivel> niveis = new ArrayList<CuboNivel>();
    private List<Measure> metricas = new ArrayList<Measure>();
    private List<Filter> filtros = new ArrayList<Filter>();

    public Cube() {
    }

    public Cube(String esquema, String tabela) {
        this.esquema = esquema;
        this.tabela = tabela;
    }

    /**
     * @return the filtros
     */
    public List<Filter> getFiltros() {
        return filtros;
    }

    /**
     * @param filtros the filtros to set
     */
    public void setFiltros(List<Filter> filtros) {
        this.filtros = filtros;
    }

    /**
     * @return the metricas
     */
    public List<Measure> getMetricas() {
        return metricas;
    }

    /**
     * @param metricas the metricas to set
     */
    public void setMetricas(List<Measure> metricas) {
        this.metricas = metricas;
    }

    /**
     * @return the esquema
     */
    public String getSchema() {
        return esquema;
    }

    /**
     * @param esquema the esquema to set
     */
    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    /**
     * @return the tabela
     */
    public String getTable() {
        return tabela;
    }

    /**
     * @param tabela the tabela to set
     */
    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    @Override
    public void accept(MetadataEntityVisitor visitor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the niveis
     */
    public List<CuboNivel> getNiveis() {
        return niveis;
    }

    /**
     * @param niveis the niveis to set
     */
    public void setNiveis(List<CuboNivel> niveis) {
        this.niveis = niveis;
    }
}
