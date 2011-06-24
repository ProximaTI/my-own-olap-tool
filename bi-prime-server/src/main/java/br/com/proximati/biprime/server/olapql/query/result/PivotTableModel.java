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
    private PivotTableNodeRoot rowsRoot = new PivotTableNodeRoot();
    /** root node, related to column fields */
    private PivotTableNodeRoot columnsRoot = new PivotTableNodeRoot();
    /** values and nodes */
    private Map<Pair<PivotTableNode, PivotTableNode>, Number> values = new HashMap<Pair<PivotTableNode, PivotTableNode>, Number>();

    public Map<Pair<PivotTableNode, PivotTableNode>, Number> getValues() {
        return values;
    }

    public void setValues(Map<Pair<PivotTableNode, PivotTableNode>, Number> values) {
        this.values = values;
    }

    public PivotTableNodeRoot getColumnsRoot() {
        return columnsRoot;
    }

    public void setColumnsRoot(PivotTableNodeRoot columnsRoot) {
        this.columnsRoot = columnsRoot;
    }

    public PivotTableNodeRoot getRowsRoot() {
        return rowsRoot;
    }

    public void setRowsRoot(PivotTableNodeRoot rowsRoot) {
        this.rowsRoot = rowsRoot;
    }
}
