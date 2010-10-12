package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Cube;

public class CubeEdit {
    public static final String CUBE_EDIT_BEAN_NAME = "cubeEdit";
    public static final String CUBE_EDIT_ACTION = "cubeEdit";

    private Cube cube;

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

    // m�trica

    public void insertMeasure() {

    }

    public void editMeasure() {

    }

    public void deleteMeasure() {

    }

    public String save() {
        return null;
    }

    public String cancel() {
        return null;
    }
}
