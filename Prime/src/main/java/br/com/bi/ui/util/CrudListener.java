package br.com.bi.ui.util;

import java.util.List;

/**
 * Provide interface of custom crudhandler manages.
 * 
 * @author carlos
 *
 * @param <T>
 */
public interface CrudListener<T> {

    List<T> onList();

    T onNewInstance();

    T onEdit(T obj);

    void onSave(T obj);

    void onDelete(T obj);

    void onCancelEdit();
}
