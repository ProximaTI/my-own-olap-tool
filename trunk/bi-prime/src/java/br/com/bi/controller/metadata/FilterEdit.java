package br.com.bi.controller.metadata;

import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.util.controller.AbstractController;
import br.com.bi.util.view.jsf.Util;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

public class FilterEdit extends AbstractController {

    public static final String FILTER_EDIT_BEAN_NAME = "filterEdit";
    public static final String FILTER_EDIT_ACTION = "filterEdit";
    private Filter filter;

    public String getBeanName() {
        return FILTER_EDIT_BEAN_NAME;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * Retorna o título da janela.
     * @return
     */
    public String getTitle() {
        if (filter.getParentIndex() == -1) {
            return Util.getBundleValue("NOVO") + " "
                    + Util.getBundleValue("FILTRO");
        } else {
            return Util.getBundleValue("ALTERAR") + " "
                    + Util.getBundleValue("FILTRO");
        }
    }

    /**
     * @return
     */
    public CubeEdit getCubeEdit() {
        return (CubeEdit) Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
    }
}
