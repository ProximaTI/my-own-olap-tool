/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;


import br.com.bi.util.view.annotation.DisplayProperty;
import br.com.bi.util.view.annotation.TableViewColumn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class Level extends IdentifiedEntity {

    private List<Property> properties = new ArrayList<Property>();
    private String joinColumnUpperLevel;
    private String schema;
    private String table;
    private int index;

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
    @TableViewColumn(bundleKey = "TABELA", index = 2)
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
    @TableViewColumn(bundleKey = "ESQUEMA", index = 1)
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
    public int getIndex() {
        return index;
    }

    /**
     * @param index the indice to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    @TableViewColumn(bundleKey = "NOME", index = 0)
    public String getName() {
        return super.getName();
    }

    @DisplayProperty
    public String getDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(getName()).append("]");
        return sb.toString();
    }
}