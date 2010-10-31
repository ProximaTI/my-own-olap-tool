package br.com.bi.control.metadata;


import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.util.Closure;
import br.com.bi.util.controller.AbstractController;
import br.com.bi.util.view.adf.lov.AnnotationBasedLovModel;
import br.com.bi.util.view.jsf.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;


public class CubeLevelEdit extends AbstractController {
    public static final String CUBE_LEVEL_EDIT_BEAN_NAME = "cubeLevelEdit";
    public static final String CUBE_LEVEL_EDIT_ACTION = "cubeLevelEdit";

    private AnnotationBasedLovModel levelsLovModel;
    private List<Level> levels;
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

    public List<SelectItem> getColumns() {
        List<SelectItem> itens = new ArrayList<SelectItem>();

        List<String> columns =
            MetadataFacade.getInstance().findColumnsByTable(getCubeEdit().getCube().getSchema(),
                                                            getCubeEdit().getCube().getTable());

        for (String column : columns)
            itens.add(new SelectItem(column, column));
        return itens;
    }

    /**
     * @return
     */
    public CubeEdit getCubeEdit() {
        return (CubeEdit)Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
    }

    /**
     * @return
     */
    public List<Level> getLevels() {
        if (levels == null)
            levels = MetadataFacade.getInstance().findAllLevels();
        return levels;
    }

    /**
     * @return
     */
    public String getBeanName() {
        return CUBE_LEVEL_EDIT_BEAN_NAME;
    }

    /**
     * Retorna o modelo para o lov de níveis.
     * @return
     */
    public AnnotationBasedLovModel getLevelsLovModel() {
        if (levelsLovModel == null) {
            levelsLovModel =
                    new AnnotationBasedLovModel<CubeLevel, Level>(CubeLevel.class,
                                                                  new Closure<CubeLevel>() {
                        public CubeLevel evaluate() {
                            return cubeLevel;
                        }
                    }, Level.class, new Closure<List<Level>>() {
                        public List<Level> evaluate() {
                            return getLevels();
                        }
                    });
        }

        return levelsLovModel;
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
            if (cubeLevel.getParentIndex() == -1) {
                cubeLevel.setParentIndex(getCubeEdit().getCube().getCubeLevels().size());
                getCubeEdit().getCube().getCubeLevels().add(cubeLevel);
            } else {
                getCubeEdit().getCube().getCubeLevels().set(cubeLevel.getParentIndex(),
                                                            cubeLevel);
            }

        popup.hide();

        dispose();
    }

    /**
     * @param popupFetchEvent
     */
    public void popupFetched(PopupFetchEvent popupFetchEvent) {
        if (popupFetchEvent.getLaunchSourceClientId().contains(CubeEdit.BTN_INSERT))
            cubeLevel = new CubeLevel();
        else
            cubeLevel = getCubeEdit().getSelectedCubeLevel().clone();
    }
}
