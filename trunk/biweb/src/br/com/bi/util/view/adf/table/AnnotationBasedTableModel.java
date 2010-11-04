package br.com.bi.util.view.adf.table;


import br.com.bi.util.Closure;
import br.com.bi.util.view.annotation.TableViewColumn;
import br.com.bi.util.view.jsf.Util;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private Class<T> listType;

    public AnnotationBasedTableModel(Class<T> listType,
                                     Closure<List<T>> lazyList) {
        this.collectionModel = new LazyListBasedCollectionModel<T>(lazyList);
        this.listType = listType;
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

            for (Method method : listType.getMethods()) {
                if (method.isAnnotationPresent(TableViewColumn.class)) {
                    ColumnDescriptorImpl descriptor =
                        new ColumnDescriptorImpl(method);

                    descriptors.add(descriptor);
                }
            }
        }

        Collections.sort(descriptors, new Comparator() {

                public int compare(Object o1, Object o2) {
                    ColumnDescriptorImpl cd1 =
                        (AnnotationBasedTableModel.ColumnDescriptorImpl)o1;
                    ColumnDescriptorImpl cd2 =
                        (AnnotationBasedTableModel.ColumnDescriptorImpl)o2;
                    return cd1.getIndex().compareTo(cd2.getIndex());
                }
            });

        return descriptors;
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
            return listType;
        }

        public boolean isReadOnly() {
            return true;
        }

        public boolean isRequired() {
            return false;
        }

        public Integer getIndex() {
            return method.getAnnotation(TableViewColumn.class).index();
        }
    }
}
