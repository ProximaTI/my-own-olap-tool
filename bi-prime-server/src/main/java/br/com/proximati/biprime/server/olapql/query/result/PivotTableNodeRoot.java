/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.query.result;

/**
 *
 * @author rnpcapes
 */
public class PivotTableNodeRoot extends PivotTableNode {

    /** distance from root to deeper leaf */
    private int distanceToDeeperLeaf;

    /**
     * @return the distanceToDeeperLeaf
     */
    public int getDistanceToDeeperLeaf() {
        return distanceToDeeperLeaf;
    }

    /**
     * @param distanceToDeeperLeaf the distanceToDeeperLeaf to set
     */
    public void setDistanceToDeeperLeaf(int distanceToDeeperLeaf) {
        this.distanceToDeeperLeaf = distanceToDeeperLeaf;
    }
}
