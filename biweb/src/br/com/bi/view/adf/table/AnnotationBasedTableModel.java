package br.com.bi.view.adf.table;


import br.com.bi.view.annotation.TableViewColumn;
import br.com.bi.view.jsf.Util;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ColumnDescriptor;
import oracle.adf.view.rich.model.TableModel;

import org.apache.myfaces.trinidad.model.CollectionModel;

import org.springframework.util.StringUtils;


/**
 * Table model baseado em anotações.
 *
 * @author Luiz Augusto
 */
public class AnnotationBasedTableModel<T> extends TableModel {
    private List<ColumnDescriptor> descriptors;
    private CollectionModel collectionModel;
    private Class clazz;

    public AnnotationBasedTableModel(Class<T> clazz, List<T> data) {
        this.collectionModel = new ListBasedCollectionModel(data);
        this.clazz = clazz;
    }

    public CollectionModel getCollectionModel() {
        return collectionModel;
    }

    /**
     * Retorna a lista de descritores das colunas das tabelas.
     * @return
     */
    public List<ColumnDescriptor> getColumnDescriptors() {
        if (descriptors == null) {
            descriptors = new ArrayList<ColumnDescriptor>();

            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(TableViewColumn.class)) {
                    ColumnDescriptor descriptor =
                        new ColumnDescriptorImpl(method);

                    descriptors.add(descriptor);
                }
            }
        }
        return descriptors;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    class ColumnDescriptorImpl extends ColumnDescriptor {
        private Method method;

        ColumnDescriptorImpl(Method method) {
            this.method = method;
        }

        public int getWidth() {
            return 0;
        }

        public String getAlign() {
            return null;
        }

        public AttributeDescriptor.ComponentType getComponentType() {
            return AttributeDescriptor.ComponentType.inputText;
        }

        public String getDescription() {
            return null;
        }

        public String getLabel() {
            return Util.getBundleValue(method.getAnnotation(TableViewColumn.class).bundleKey());
        }

        public int getLength() {
            return 0;
        }

        public int getMaximumLength() {
            return 0;
        }

        public Object getModel() {
            try {
                if (collectionModel.getRowData() != null)
                    return method.invoke(collectionModel.getRowData(), null);
            } catch (Exception e) {
            }
            return null;
        }

        public String getName() {
            return StringUtils.uncapitalize(method.getName().replace("get",
                                                                     ""));
        }

        public Set<AttributeDescriptor.Operator> getSupportedOperators() {
            return Collections.emptySet();
        }

        public Class getType() {
            return clazz;
        }

        public boolean isReadOnly() {
            return true;
        }

        public boolean isRequired() {
            return false;
        }
    }
}
