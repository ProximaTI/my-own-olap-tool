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
public class Cubo extends MetadataEntity {

    private String esquema;
    private String tabela;
    private List<CuboNivel> niveis = new ArrayList<CuboNivel>();
    private List<Metrica> metricas = new ArrayList<Metrica>();
    private List<Filtro> filtros = new ArrayList<Filtro>();

    public Cubo() {
    }

    public Cubo(String esquema, String tabela) {
        this.esquema = esquema;
        this.tabela = tabela;
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

    /**
     * @return the esquema
     */
    public String getEsquema() {
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
    public String getTabela() {
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
