/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.server;

import br.com.bi.model.entity.metadata.Metadata;
import br.com.bi.olapql.Cell;
import br.com.bi.olapql.CellSet;
import java.util.List;

/**
 *
 * @author luiz
 */
public class CellSetImpl implements CellSet {

    @Override
    public Integer getPositionsAt(int axis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer getPositionsAt(int axis, List<Integer> coordinates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getMemberDisplayAt(int axis, List<Integer> coordinates) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Cell getCell(List<Integer> coordinatesAtRows, List<Integer> coordinatesAtColumns) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Metadata getMetadataAt(int axis, List<Integer> coordinate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
