package br.com.bi.control.metadata;

import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.view.jsf.Util;

public class CubeEdit extends MetadataEntityEdit {
    public static final String CUBE_EDIT_BEAN_NAME = "cubeEdit";
    public static final String CUBE_EDIT_ACTION = "cubeEdit";

    private Cube cube = new Cube();

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

    // dimension

    public void insertDimension() {

    }

    public void editDimension() {

    }

    public void deleteDimension() {

    }

    // filtro

    public void insertFilter() {

    }

    public void editFilter() {

    }

    public void deleteFilter() {

    }

    // métrica

    public void insertMeasure() {

    }

    public void editMeasure() {

    }

    public void deleteMeasure() {

    }

    public String save() {
        MetadataFacade.getInstance().save(cube);
        return CubeCad.CUBE_CAD_ACTION;
    }

    public String cancel() {
      return CubeCad.CUBE_CAD_ACTION;
    }
}
