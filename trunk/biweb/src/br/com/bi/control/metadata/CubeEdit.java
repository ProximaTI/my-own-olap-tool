package br.com.bi.control.metadata;


import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.view.jsf.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;


/**
 * Controle para a edição de um cubo.
 *
 * @author Luiz Augusto
 */
public class CubeEdit {
    public static final String CUBE_EDIT_BEAN_NAME = "cubeEdit";
    public static final String CUBE_EDIT_ACTION = "cubeEdit";
    public static final String CUBE_LEVEL_POPUP = "cubeLevelPopup";
    public static final String BTN_INSERT = "btnInsert";
    public static final String BTN_EDIT = "btnEdit";

    private RichTable tblCubeLevel;

    private Cube cube;

    /**
     * Retorna o título da apresentação.
     * @return
     */
    public String getTitle() {
        if (cube.isPersisted())
            return Util.getBundleValue("ALTERAR_CUBO");
        else
            return Util.getBundleValue("NOVO_CUBO");
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    // filtro

    public void deleteFilter() {

    }

    // métrica

    public void deleteMeasure() {

    }

    /**
     * Retorna uma lista de itens de seleção com os esquemas do banco de dados.
     * @return
     */
    public List<SelectItem> getSchemas() {
        List<SelectItem> itens = new ArrayList<SelectItem>();

        List<String> schemas = MetadataFacade.getInstance().findAllSchemas();

        for (String schema : schemas) {
            itens.add(new SelectItem(schema, schema));
        }
        return itens;
    }

    /**
     * Retorna uma lista de itens de seleção com as tabelas de um dados esquema.
     * @return
     */
    public List<SelectItem> getTables() {
        List<SelectItem> itens = new ArrayList<SelectItem>();

        List<String> tables =
            MetadataFacade.getInstance().findTablesBySchema(cube.getSchema());

        for (String table : tables) {
            itens.add(new SelectItem(table, table));
        }
        return itens;
    }

    /**
     * Salva o cubo no banco de dados.
     * @return
     */
    public String save() {
        MetadataFacade.getInstance().save(cube);
        return CubeCad.CUBE_CAD_ACTION;
    }

    /**
     * Abandona a edição do cubo.
     * @return
     */
    public String cancel() {
        return CubeCad.CUBE_CAD_ACTION;
    }

    public CubeLevel getSelectedCubeLevel() {
        return (CubeLevel)tblCubeLevel.getSelectedRowData();
    }

    public void setTblCubeLevel(RichTable tblCubeLevel) {
        this.tblCubeLevel = tblCubeLevel;
    }

    public RichTable getTblCubeLevel() {
        return tblCubeLevel;
    }
}
