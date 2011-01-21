/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.query;

import br.com.bi.model.entity.metadata.Cube;

/**
 *
 * @author Luiz
 */
public class Query {

    private Cube cube;
    private Node rows = new Node();
    private Node columns = new Node();
    private String filterExpression;

    public Query(Cube cubo) {
        this.cube = cubo;
    }

    /**
     * @return the cubo
     */
    public Cube getCube() {
        return cube;
    }

    /**
     * @param cube the cubo to set
     */
    public void setCube(Cube cube) {
        this.cube = cube;
    }

    /**
     * @return the linha
     */
    public Node getRows() {
        return rows;
    }

    /**
     * @return the column
     */
    public Node getColumns() {
        return columns;
    }

    /**
     * @return the filterExpression
     */
    public String getFilterExpression() {
        return filterExpression;
    }

    /**
     * @param filterExpression the filterExpression to set
     */
    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }
}
