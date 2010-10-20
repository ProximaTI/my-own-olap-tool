package br.com.bi.control.metadata;


import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.view.jsf.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.event.PopupFetchEvent;


public class CubeLevelEdit {
    public static final String CUBE_LEVEL_EDIT_BEAN_NAME = "cubeLevelEdit";
    public static final String CUBE_LEVEL_EDIT_ACTION = "cubeLevelEdit";

    private CubeLevel cubeLevel;

    public void setCubeLevel(CubeLevel cubeLevel) {
        this.cubeLevel = cubeLevel;
    }

    public CubeLevel getCubeLevel() {
        return cubeLevel;
    }

    /**
     * Retorna o título da janela.
     * @return
     */
    public String getTitle() {
        if (cubeLevel.getParentIndex() == -1)
            return Util.getBundleValue("VINCULAR_NOVO_NIVEL");
        else
            return Util.getBundleValue("ALTERAR_VINCULACAO_NIVEL");
    }

    public String save() {
        return null;
    }

    public String cancel() {
        return null;
    }

    public void popupFetched(PopupFetchEvent popupFetchEvent) {
        if (popupFetchEvent.getLaunchSourceClientId().contains(CubeEdit.BTN_INSERT))
            cubeLevel = new CubeLevel();
        else
            cubeLevel = getCubeEdit().getSelectedCubeLevel().clone();
    }

    public List<SelectItem> getColumns() {
        List<SelectItem> itens = new ArrayList<SelectItem>();

        List<String> columns =
            MetadataFacade.getInstance().findColumnsByTable(getCubeEdit().getCube().getSchema(),
                                                            getCubeEdit().getCube().getTable());

        for (String column : columns)
            itens.add(new SelectItem(column, column));
        return itens;
    }

    public CubeEdit getCubeEdit() {
        return (CubeEdit)Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
    }
}
