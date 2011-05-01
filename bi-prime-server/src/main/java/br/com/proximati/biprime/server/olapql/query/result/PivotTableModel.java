/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import java.util.HashMap;

/**
 *
 * @author luiz
 */
public class PivotTableModel {

    /** root node, related to row fields */
    private Node rowsRoot = null;
    /** root node, related to column fields */
    private Node columnsRoot = null;
    /** values and theirs coordinates */
    private HashMap<Pair<String>, Number> values;

    public HashMap<Pair<String>, Number> getValues() {
        return values;
    }

    public void setValues(HashMap<Pair<String>, Number> values) {
        this.values = values;
    }

    public Node getColumnsRoot() {
        return columnsRoot;
    }

    public void setColumnsRoot(Node columnsRoot) {
        this.columnsRoot = columnsRoot;
    }

    public Node getRowsRoot() {
        return rowsRoot;
    }

    public void setRowsRoot(Node rowsRoot) {
        this.rowsRoot = rowsRoot;
    }
}
