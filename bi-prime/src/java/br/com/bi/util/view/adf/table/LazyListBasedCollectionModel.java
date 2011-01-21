package br.com.bi.util.view.adf.table;


import br.com.bi.util.Closure;

import java.util.List;

import org.apache.myfaces.trinidad.model.SortableModel;


/**
 * Implementação de CollectionModel baseada em lista.
 *
 * @author Luiz Augusto
 */
public class LazyListBasedCollectionModel<T> extends SortableModel {
    private int rowIndex;
    private Closure<List<T>> lazyList;

    public LazyListBasedCollectionModel(Closure<List<T>> list) {
        this.lazyList = list;
    }

    public Object getRowKey() {
        return Integer.valueOf(rowIndex);
    }

    public void setRowKey(Object object) {
        if (object instanceof Integer)
            this.rowIndex = (Integer)object;
    }

    public boolean isRowAvailable() {
        return rowIndex > -1 && rowIndex < lazyList.evaluate().size();
    }

    public int getRowCount() {
        return lazyList.evaluate().size();
    }

    public Object getRowData() {
        return lazyList.evaluate().get(rowIndex);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int i) {
        this.rowIndex = i;
    }

    public Object getWrappedData() {
        return lazyList.evaluate();
    }

    public void setWrappedData(Object object) {

    }
}
