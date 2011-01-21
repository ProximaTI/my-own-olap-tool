package br.com.bi.controller.metadata;

import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.util.controller.AbstractController;
import br.com.bi.util.view.jsf.Util;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.model.SelectItem;
public class CubeLevelEdit extends AbstractController {

    public static final String CUBE_LEVEL_EDIT_BEAN_NAME = "cubeLevelEdit";
    public static final String CUBE_LEVEL_EDIT_ACTION = "cubeLevelEdit";
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
        if (cubeLevel.getParentIndex() == -1) {
            return Util.getBundleValue("VINCULAR_NOVO_NIVEL");
        } else {
            return Util.getBundleValue("ALTERAR_VINCULACAO_NIVEL");
        }
    }

    public List<SelectItem> getColumns() {
        List<SelectItem> itens = new ArrayList<SelectItem>();

        List<String> columns =
                MetadataFacade.getInstance().findColumnsByTable(getCubeEdit().getCube().getSchemaName(),
                getCubeEdit().getCube().getTableName());

        for (String column : columns) {
            itens.add(new SelectItem(column, column));
        }
        return itens;
    }

    /**
     * @return
     */
    public CubeEdit getCubeEdit() {
        return (CubeEdit) Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
    }

    /**
     * @return
     */
    public List<Level> getLevels() {
        if (levels == null) {
            levels = MetadataFacade.getInstance().findAllLevels();
        }
        return levels;
    }

    /**
     * @return
     */
    public String getBeanName() {
        return CUBE_LEVEL_EDIT_BEAN_NAME;
    }
}
