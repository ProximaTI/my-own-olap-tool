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
public class Level extends MetadataEntity {

    private String schema;
    private String table;
    private String joinColumnUpperLevel;
    private List<Property> properties = new ArrayList<Property>();
    private int indice;

    public Level() {
    }

    public Level(String schema, String table) {
        this.schema = schema;
        this.table = table;
    }

    public Level(String schema, String table, String joinColumn) {
        this.schema = schema;
        this.table = table;
        this.joinColumnUpperLevel = joinColumn;
    }

    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the properties
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return the joinColumnUpperLevel
     */
    public String getJoinColumnUpperLevel() {
        return joinColumnUpperLevel;
    }

    /**
     * @param joinColumnUpperLevel the joinColumn to set
     */
    public void setJoinColumnUpperLevel(String joinColumnUpperLevel) {
        this.joinColumnUpperLevel = joinColumnUpperLevel;
    }

    public Property getCodeProperty() {
        for (Property p : properties) {
            if (p.isCodeProperty()) {
                return p;
            }
        }
        return null;
    }

    public Property getNameProperty() {
        for (Property p : properties) {
            if (p.isNameProperty()) {
                return p;
            }
        }
        return null;
    }

    /**
     * @return the indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }
}
