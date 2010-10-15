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
public class Cube extends IdentifiedEntity {

    private String schema;
    private String table;
    private List<CubeLevel> levels = new ArrayList<CubeLevel>();
    private List<Measure> measures = new ArrayList<Measure>();
    private List<Filter> filters = new ArrayList<Filter>();

    public Cube() {
    }

    public Cube(String schema, String table) {
        this.schema = schema;
        this.table = table;
    }

    /**
     * @return the filtros
     */
    public List<Filter> getFilters() {
        return filters;
    }

    /**
     * @param filters the filtros to set
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    /**
     * @return the metricas
     */
    public List<Measure> getMeasures() {
        return measures;
    }

    /**
     * @param metricas the metricas to set
     */
    public void setMeasures(List<Measure> metricas) {
        this.measures = metricas;
    }

    /**
     * @return the esquema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema the esquema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return the tabela
     */
    public String getTable() {
        return table;
    }

    /**
     * @param table the tabela to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the niveis
     */
    public List<CubeLevel> getCubeLevels() {
        return levels;
    }

    /**
     * @param levels the niveis to set
     */
    public void setCubeLevels(List<CubeLevel> levels) {
        this.levels = levels;
    }

    @Override
    public Object clone() {
        Cube clone = new Cube();

        clone.setCubeLevels(new ArrayList<CubeLevel>(this.getCubeLevels()));
        clone.setDescription(this.getDescription());
        clone.setFilters(new ArrayList<Filter>(this.getFilters()));
        clone.setMeasures(new ArrayList<Measure>(this.getMeasures()));
        clone.setName(this.getName());
        clone.setPersisted(this.isPersisted());
        clone.setSchema(this.getSchema());
        clone.setTable(this.getTable());
        clone.setId(this.getId());
        return clone;
    }
}
