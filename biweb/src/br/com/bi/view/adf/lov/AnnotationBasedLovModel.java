package br.com.bi.view.adf.lov;


import br.com.bi.view.adf.table.AnnotationBasedTableModel;

import java.util.Collections;
import java.util.List;

import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

/**
 * Implementação de InputListOfValuesModel baseado em anotações.
 * 
 * @author Luiz Augusto
 * @param <T>
 */
public class AnnotationBasedLovModel<T> extends ListOfValuesModel {
    private AnnotationBasedTableModel tableModel;

    public AnnotationBasedLovModel(Class<T> clazz, List<T> data) {
        this.tableModel = new AnnotationBasedTableModel(clazz, data);
    }

    public QueryDescriptor getQueryDescriptor() {
        return null;
    }

    public QueryModel getQueryModel() {
        return null;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public List<? extends Object> getItems() {
        return Collections.emptyList();
    }

    public List<? extends Object> getRecentItems() {
        return Collections.emptyList();
    }

    public boolean isAutoCompleteEnabled() {
        return false;
    }

    public void performQuery(QueryDescriptor queryDescriptor) {
    }

    public List<Object> autoCompleteValue(Object object) {
        return Collections.emptyList();
    }

    public void valueSelected(Object object) {
    }
}
