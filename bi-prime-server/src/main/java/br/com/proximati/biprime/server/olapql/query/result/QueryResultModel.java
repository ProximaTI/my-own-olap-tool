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
public class QueryResultModel {

    /** root node, related to row fields */
    private Node rowsRoot = new Node();
    /** root node, related to column fields */
    private Node columnsRoot = new Node();
    /** values and nodes */
    private Map<Pair<Node, Node>, Number> values = new HashMap<Pair<Node, Node>, Number>();

    public Map<Pair<Node, Node>, Number> getValues() {
        return values;
    }

    public void setValues(Map<Pair<Node, Node>, Number> values) {
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
