/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

import br.com.proximati.biprime.util.Pair;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luiz
 */
public class PivotTableModel {

    /** root node, related to row fields */
    private PivotTableNode rowsRoot = new PivotTableNode();
    /** root node, related to column fields */
    private PivotTableNode columnsRoot = new PivotTableNode();
    /** values and nodes */
    private Map<Pair<PivotTableNode, PivotTableNode>, Number> values = new HashMap<Pair<PivotTableNode, PivotTableNode>, Number>();

    public Map<Pair<PivotTableNode, PivotTableNode>, Number> getValues() {
        return values;
    }

    public void setValues(Map<Pair<PivotTableNode, PivotTableNode>, Number> values) {
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
