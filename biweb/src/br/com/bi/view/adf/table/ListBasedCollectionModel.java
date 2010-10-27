package br.com.bi.view.adf.table;


import java.util.List;

import org.apache.myfaces.trinidad.model.SortableModel;


public class ListBasedCollectionModel extends SortableModel {
    private Object rowKey;
    private int rowIndex;
    private List data;

    public ListBasedCollectionModel(List data) {
        this.data = data;
    }

    public Object getRowKey() {
        return rowKey;
    }

    public void setRowKey(Object object) {
        this.rowKey = object;
    }

    public boolean isRowAvailable() {
        return rowIndex > -1;
    }

    public int getRowCount() {
        return data.size();
    }

    public Object getRowData() {
        return data.get(rowIndex);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int i) {
        this.rowIndex = i;
    }

    public Object getWrappedData() {
        return data;
    }

    public void setWrappedData(Object object) {
        this.data = (List)object;
    }
}
