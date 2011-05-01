/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql;

import br.com.bi.model.entity.metadata.Metadata;
import java.util.List;

/**
 *
 * @author luiz
 */
public interface CellSet {

    public static int ROWS = 0;
    public static int COLUMNS = 1;

    /**
     * Tells how many members starts each axis.
     * Returns -1 if it's empty.
     * @param axis
     * @return 
     */
    public Integer getPositionsAt(int axis);

    /**
     * Tells many members are children of member identified by the partial coordinate.
     * Returns -1 if it's empty.
     * @param axis
     * @param coordinates
     * @return 
     */
    public Integer getPositionsAt(int axis, List<Integer> coordinates);

    /**
     * 
     * @param axis
     * @param coordinates
     * @return 
     */
    public String getMemberDisplayAt(int axis, List<Integer> coordinates);

    /**
     * Returns cell identified by provided coordinates.
     * @param coordinatesAtRows
     * @param coordinatesAtColumns
     * @return 
     */
    public Cell getCell(List<Integer> coordinatesAtRows, List<Integer> coordinatesAtColumns);

    /**
     * Returns metadata related to partial provided coordinate.
     * @param axis
     * @param coordinate
     * @return 
     */
    public Metadata getMetadataAt(int axis, List<Integer> coordinate);
}
