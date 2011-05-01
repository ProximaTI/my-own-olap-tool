/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql;

import java.util.List;

/**
 *
 * @author luiz
 */
public class Cell {
    // coordinates

    private List<Integer> coordinatesAtRows;
    private List<Integer> coordinatesAtColumns;
    
    private double value;

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return the coordinatesAtRows
     */
    public List<Integer> getCoordinatesAtRows() {
        return coordinatesAtRows;
    }

    /**
     * @param coordinatesAtRows the coordinatesAtRows to set
     */
    public void setCoordinatesAtRows(List<Integer> coordinatesAtRows) {
        this.coordinatesAtRows = coordinatesAtRows;
    }

    /**
     * @return the coordinatesAtColumns
     */
    public List<Integer> getCoordinatesAtColumns() {
        return coordinatesAtColumns;
    }

    /**
     * @param coordinatesAtColumns the coordinatesAtColumns to set
     */
    public void setCoordinatesAtColumns(List<Integer> coordinatesAtColumns) {
        this.coordinatesAtColumns = coordinatesAtColumns;
    }
}
