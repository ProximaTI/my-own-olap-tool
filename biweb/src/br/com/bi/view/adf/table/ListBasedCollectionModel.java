package br.com.bi.view.adf.table;


import java.util.List;

import org.apache.myfaces.trinidad.model.SortableModel;


public class ListBasedCollectionModel extends SortableModel {
    private int rowIndex;
    private List data;

    public ListBasedCollectionModel(List data) {
        this.data = data;
    }

    public Object getRowKey() {
        return Integer.valueOf(rowIndex);
    }

    public void setRowKey(Object object) {
        if (object instanceof Integer)
            this.rowIndex = (Integer)object;
    }

    public boolean isRowAvailable() {
        return rowIndex > -1 && rowIndex < data.size();
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
