package br.com.bi.control.metadata;


import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.util.view.jsf.Util;

import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTable;


/**
 * Controle para tela com listagem dos cubos.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class CubeCad {
    public static final String CUBE_CAD_BEAN_NAME = "cubeCad";
    public static final String CUBE_CAD_ACTION = "cubeCad";

    private RichTable table;

    /**
     * Retorna uma lista com os cubos persistidos.
     * @return
     */
    public List<Cube> getCubes() {
        return MetadataFacade.getInstance().findAllCubes();
    }

    /**
     * Altera o cubo selecionado.
     * @return
     */
    public String edit() {
        Cube selectedCube = (Cube)table.getSelectedRowData();

        // realiza a carga completa do cubo
        selectedCube =
                MetadataFacade.getInstance().findCubeById(selectedCube.getId());

        CubeEdit cubeEdit =
            (CubeEdit)Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
        cubeEdit.setCube((Cube)selectedCube.clone());
        return CubeEdit.CUBE_EDIT_ACTION;
    }

    /**
     * Insere um novo cubo no banco de dados.
     * @return
     */
    public String insert() {
        CubeEdit cubeEdit =
            (CubeEdit)Util.getELVar(CubeEdit.CUBE_EDIT_BEAN_NAME);
        cubeEdit.setCube(new Cube());
        return CubeEdit.CUBE_EDIT_ACTION;
    }

    /**
     * Apaga o cubo selecionado.
     * @return
     */
    public String delete() {
        Cube cube = (Cube)table.getSelectedRowData();

        MetadataFacade.getInstance().deleteCube(cube.getId());
        return null;
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }
}
