package br.com.bi.controller.metadata;

import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.util.view.jsf.Util;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Controle para a edição de um cubo.
 *
 * @author Luiz Augusto
 */
public class CubeEdit {

    public static final String CUBE_EDIT_BEAN_NAME = "cubeEdit";
    public static final String CUBE_EDIT_ACTION = "cubeEdit";
    public static final String BTN_INSERT = "btnInsert";
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
        if (cube.getId() != null) {
            return Util.getBundleValue("ALTERAR_CUBO");
        } else {
            return Util.getBundleValue("NOVO_CUBO");
        }
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
                    BiFacade.getInstance().findAllSchemas();

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
                    BiFacade.getInstance().findTablesBySchema(cube.getSchemaName());

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
        BiFacade.getInstance().saveCube(cube);
        return CubeCad.CUBE_CAD_ACTION;
    }

    /**
     * Abandona a edição do cubo.
     * @return
     */
    public String cancel() {
        return CubeCad.CUBE_CAD_ACTION;
    }

    /**
     * @param valueChangeEvent
     */
    public void schemaChanged(ValueChangeEvent valueChangeEvent) {
        cube.setSchemaName((String) valueChangeEvent.getNewValue());
        cube.setTableName(null);
        tables = null;
    }

    /**
     * @param valueChangeEvent
     */
    public void tableChanged(ValueChangeEvent valueChangeEvent) {
        cube.setTableName((String) valueChangeEvent.getNewValue());
        tables = null;
    }
}
