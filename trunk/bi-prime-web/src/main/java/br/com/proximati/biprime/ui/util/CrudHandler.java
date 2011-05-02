package br.com.proximati.biprime.ui.util;

import java.util.List;

/**
 * class responsable of manage data in basic view.
 * 
 * @author carlos
 *
 * @param <T>
 */
public class CrudHandler<T> {

    private CrudListener<T> listener;
    private List<T> list;
    private T selected;

    public CrudHandler(CrudListener<T> listener) {
        this.listener = listener;
    }

    public void newInstance() {
        selected = listener.onNewInstance();
    }

    public void list() {
        list = listener.onList();
        selected = null;
    }

    public void edit(T obj) {
        selected = listener.onEdit(obj);
    }

    public void save(T obj) {
        listener.onSave(obj);
        this.list();
    }

    public void delete(T obj) {
        listener.onDelete(obj);
        this.list();
    }

    public void cancelEdit() {
        listener.onCancelEdit();
        this.list();
    }

    public T getSelected() {
        return selected;
    }

    public List<T> getList() {
        return list;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }
}
