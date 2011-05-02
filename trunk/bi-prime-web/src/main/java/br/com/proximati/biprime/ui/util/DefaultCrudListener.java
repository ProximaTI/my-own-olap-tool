package br.com.proximati.biprime.ui.util;

import java.util.List;

/**
 * Default Listener of support custom operation in CrudHandler
 * @author carlos
 *
 * @param <T>
 */
public abstract class DefaultCrudListener<T> implements CrudListener<T> {

    @Override
    public List<T> onList() {
        return null;
    }

    @Override
    public T onNewInstance() {
        return null;
    }

    @Override
    public T onEdit(T obj) {
        return obj;
    }

    @Override
    public void onSave(T obj) {
    }

    @Override
    public void onDelete(T obj) {
    }

    @Override
    public void onCancelEdit() {
    }
}
