/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.util;

import br.com.proximati.biprime.server.olapql.query.result.PivotTableNode;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableNodeRoot;

/**
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class DepthFirstSearch {

    private TraversingListener listener;

    public DepthFirstSearch(TraversingListener listener) {
        this.listener = listener;
    }

    public void perform(PivotTableNodeRoot s) {
        listener.visitingRoot(s);

        dfsVisit(s, s);
    }

    public void dfsVisit(PivotTableNodeRoot s, PivotTableNode u) {
        for (PivotTableNode v : u.getChildrenNodes()) {
            if (v.getChildrenNodes().isEmpty())
                listener.visitingLeaf(s, v);
            else
                listener.visitingNonLeaf(s, v);

            dfsVisit(s, v);
        }
    }

    /**
     * @return the listener
     */
    public TraversingListener getListener() {
        return listener;
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(TraversingListener listener) {
        this.listener = listener;
    }
}
