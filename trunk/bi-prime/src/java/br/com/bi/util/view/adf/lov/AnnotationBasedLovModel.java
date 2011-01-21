package br.com.bi.util.view.adf.lov;


import br.com.bi.util.Closure;
import br.com.bi.util.view.adf.table.AnnotationBasedTableModel;
import br.com.bi.util.view.annotation.DisplayProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import oracle.adf.view.rich.model.ListOfValuesModel;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.model.RowKeySetImpl;


/**
 * Implementação de InputListOfValuesModel baseado em anotações.
 *
 * @author Luiz Augusto
 * @param <T>
 */
public class AnnotationBasedLovModel<T, L> extends ListOfValuesModel {
    private Method targetSetterMethod;
    private Method targetGetterMethod;
    private Method displayMethod;
    private Closure<List<L>> lazyList;
    private Class<T> targetType;
    private Closure<T> target;
    private Class<L> listType;
    private AnnotationBasedTableModel<L> tableModel;

    public AnnotationBasedLovModel(Class<T> targetType, Closure<T> target,
                                   Class<L> listType,
                                   Closure<List<L>> lazyList) {
        this.targetType = targetType;
        this.target = target;
        this.listType = listType;
        this.lazyList = lazyList;

        init();
    }

    private void init() {
        this.tableModel = new AnnotationBasedTableModel<L>(listType, lazyList);

        for (Method method : targetType.getMethods()) {
            if (method.getParameterTypes().length == 1 &&
                method.getParameterTypes()[0].equals(listType)) {
                if (method.getName().startsWith("set"))
                    targetSetterMethod = method;
            }

            if (method.getName().startsWith("get") &&
                method.getReturnType().equals(listType)) {
                targetGetterMethod = method;
            }
        }

        for (Method method : listType.getMethods())
            if (method.isAnnotationPresent(DisplayProperty.class)) {
                displayMethod = method;
                break;
            }
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

    /**
     * Método que responde pela seleção de um objeto na lista.
     * @param object
     */
    public void valueSelected(Object object) {
        RowKeySetImpl keySet = (RowKeySetImpl)object;

        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            tableModel.getCollectionModel().setRowKey(it.next());
        }

        object = tableModel.getCollectionModel().getRowData();

        try {
            targetSetterMethod.invoke(target.evaluate(),
                                      new Object[] { object });
        } catch (Exception e) {
            // TODO
        }
    }

    /**
     * Retorna o objeto que está atribuído no objeto alvo.
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object getSelectedObject() throws IllegalAccessException,
                                              InvocationTargetException {
        return targetGetterMethod.invoke(target.evaluate(), null);
    }

    /**
     * Retorna a string correspondente à descrição do objeto selecionado.
     * @return
     */
    public String getDisplay() {
        try {
            if (getSelectedObject() != null)
                return displayMethod.invoke(getSelectedObject(),
                                            null).toString();
        } catch (Exception e) {
            // TODO
        }
        return null;
    }

    public void setDisplay(String display) {

    }
}
