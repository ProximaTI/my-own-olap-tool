package br.com.bi.control.metadata;


import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.util.view.jsf.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
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

    private Cube cube;

    /**
     * @return
     */
    public Cube getCube() {
        return cube;
    }

    /**
     * @param cube
     */
    public void setCube(Cube cube) {
        this.cube = cube;
    }

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

    private RichTable tblCubeLevel;

    /**
     * @return
     */
    public CubeLevel getSelectedCubeLevel() {
        return (CubeLevel)tblCubeLevel.getSelectedRowData();
    }

    /**
     * @param tblCubeLevel
     */
    public void setTblCubeLevel(RichTable tblCubeLevel) {
        this.tblCubeLevel = tblCubeLevel;
    }

    /**
     * @return
     */
    public RichTable getTblCubeLevel() {
        return tblCubeLevel;
    }

    private List<SelectItem> schemas;

    /**
     * Retorna uma lista de itens de seleção com os esquemas do banco de dados.
     * @return
     */
    public List<SelectItem> getSchemas() {
        if (schemas == null) {
            schemas = new ArrayList<SelectItem>();

            List<String> _schemas =
                MetadataFacade.getInstance().findAllSchemas();

            for (String schema : _schemas) {
                schemas.add(new SelectItem(schema, schema));
            }
        }
        return schemas;
    }

    private List<SelectItem> tables;

    /**
     * Retorna uma lista de itens de seleção com as tabelas de um dados esquema.
     * @return
     */
    public List<SelectItem> getTables() {
        if (tables == null) {
            tables = new ArrayList<SelectItem>();

            List<String> _tables =
                MetadataFacade.getInstance().findTablesBySchema(cube.getSchema());

            for (String table : _tables) {
                tables.add(new SelectItem(table, table));
            }
        }
        return tables;
    }

    // ===============
    // ==== Ações ====
    // ===============

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

    // filtro

    public void deleteFilter() {

    }

    // métrica

    public void deleteMeasure() {

    }

    public void deleteCubeLevel(ActionEvent actionEvent) {
        cube.getCubeLevels().remove(getSelectedCubeLevel());
    }

    public void cubeLevelChanged(ValueChangeEvent valueChangeEvent) {
        cube.setSchema((String)valueChangeEvent.getNewValue());
        cube.setTable(null);
        tables = null;
    }
}
