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
    private PivotTableNode rowsRoot = null;
    /** root node, related to column fields */
    private PivotTableNode columnsRoot = null;
    /** values and nodes */
    private HashMap<Pair<PivotTableNode, PivotTableNode>, Number> values;

    public HashMap<Pair<PivotTableNode, PivotTableNode>, Number> getValues() {
        return values;
    }

    public void setValues(HashMap<Pair<PivotTableNode, PivotTableNode>, Number> values) {
        this.values = values;
    }

    public PivotTableNode getColumnsRoot() {
        return columnsRoot;
    }

    public void setColumnsRoot(PivotTableNode columnsRoot) {
        this.columnsRoot = columnsRoot;
    }

    public PivotTableNode getRowsRoot() {
        return rowsRoot;
    }

    public void setRowsRoot(PivotTableNode rowsRoot) {
        this.rowsRoot = rowsRoot;
    }
}
