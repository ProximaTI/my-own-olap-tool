package br.com.bi.controller.metadata;

import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.util.view.jsf.Util;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Controle para tela com listagem dos cubos.
 *
 * @author Luiz Augusto Garcia da Silva
 */
@ManagedBean
@SessionScoped
public class CubeCad {

    public static final String CUBE_CAD_BEAN_NAME = "cubeCad";
    public static final String CUBE_CAD_ACTION = "cubeCad";
    private Cube selectedCube;

    /**
     * Retorna uma lista com os cubos persistidos.
     * @return
     */
    public List<Cube> getCubes() {
        return BiFacade.getInstance().findAllCubes();
    }

    /**
     * Altera o cubo selecionado.
     * @return
     */
    public String edit() {
        // realiza a carga completa do cubo
        setSelectedCube(BiFacade.getInstance().findCubeById(getSelectedCube().getId()));

        CubeEdit cubeEdit = (CubeEdit) Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
        cubeEdit.setCube((Cube) getSelectedCube().clone());
        return CubeEdit.CUBE_EDIT_ACTION;
    }

    /**
     * Insere um novo cubo no banco de dados.
     * @return
     */
    public String insert() {
        CubeEdit cubeEdit = (CubeEdit) Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
        cubeEdit.setCube(new Cube());
        return CubeEdit.CUBE_EDIT_ACTION;
    }

    /**
     * Apaga o cubo selecionado.
     * @return
     */
    public String delete() {
        BiFacade.getInstance().deleteCube(getSelectedCube().getId());
        return null;
    }

    /**
     * @return the selectedCube
     */
    public Cube getSelectedCube() {
        return selectedCube;
    }

    /**
     * @param selectedCube the selectedCube to set
     */
    public void setSelectedCube(Cube selectedCube) {
        this.selectedCube = selectedCube;
    }
}
