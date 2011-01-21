package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.util.controller.AbstractController;
import br.com.bi.util.view.jsf.Util;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

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
        if (filter.getParentIndex() == -1)
            return Util.getBundleValue("NOVO") + " " +
                Util.getBundleValue("FILTRO");
        else
            return Util.getBundleValue("ALTERAR") + " " +
                Util.getBundleValue("FILTRO");
    }

    /**
     * @return
     */
    public CubeEdit getCubeEdit() {
        return (CubeEdit)Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
    }

    // ===============
    // ==== Ações ====
    // ===============

    /**
     * @param dialogEvent
     */
    public void dialogListener(DialogEvent dialogEvent) {
        RichPopup popup =
            (RichPopup)((RichDialog)dialogEvent.getSource()).getParent();

        if (dialogEvent.getOutcome() == DialogEvent.Outcome.ok)
            if (filter.getParentIndex() == -1) {
                filter.setParentIndex(getCubeEdit().getCube().getFilterList().size());
                getCubeEdit().getCube().addFilter(filter);
            } else {
                getCubeEdit().getCube().getFilterList().set(filter.getParentIndex(),
                                                            filter);
            }

        popup.hide();

        dispose();
    }

    /**
     * @param popupFetchEvent
     */
    public void popupFetched(PopupFetchEvent popupFetchEvent) {
        if (popupFetchEvent.getLaunchSourceClientId().contains(CubeEdit.BTN_INSERT))
            filter = new Filter();
        else
            filter = getCubeEdit().getSelectedFilter().clone();
    }
}
