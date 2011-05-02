/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.proximati.biprime.service.CubeManager;
import br.com.proximati.biprime.ui.util.SessionUtil;
import br.com.proximati.biprime.metadata.entity.Cube;

/**
 *
 * @author carlos
 */
public class CubeHandlerTest {

    private CubeHandler cubeHandler;
    private List<Cube> listOfCubes;
    @Mock
    private SessionUtil sessionUtil;
    @Mock
    private CubeManager cubeManager;

    @Before
    public void setup() {
        initMocks(this);
        cubeHandler = new CubeHandler(sessionUtil, cubeManager);
        listOfCubes = new ArrayList<Cube>();
        when(cubeManager.retriveAllCubes()).thenReturn(listOfCubes);
    }

    @Test
    public void shouldLoadCubeListOnInitialize() {
        cubeHandler.list();
        verify(cubeManager).retriveAllCubes();
        assertThat(cubeHandler.getList(), is(listOfCubes));
    }

    @Test
    public void shouldMessageErrorOnDontRetriveListCubes() {
        when(cubeManager.retriveAllCubes()).thenThrow(new RuntimeException());
        cubeHandler.list();
        verify(sessionUtil).addErrorMessage("cube.list.error");
    }

    @Test
    public void shouldEditCube() {
        cubeHandler.list();
        Cube cube = new Cube();
        cubeHandler.edit(cube);
        verify(cubeManager).retriveCube(cube.getId());
    }

    @Test
    public void shouldErrorMessageOnEditCubeWhenErrorService() {
        Cube cube = new Cube();
        when(cubeManager.retriveCube(cube.getId())).thenThrow(new RuntimeException());
        cubeHandler.list();
        cubeHandler.edit(cube);
        verify(sessionUtil).addErrorMessage("cube.edit.error");
    }

    @Test
    public void shouldCreateNewCube() {
        cubeHandler.list();
        cubeHandler.newInstance();
        assertThat(cubeHandler.getSelected(), is(notNullValue()));
    }

    @Test
    public void shouldSaveCube() {
        cubeHandler.newInstance();
        Cube cube = cubeHandler.getSelected();
        cubeHandler.save(cube);
        verify(cubeManager).save(cube);

        verify(sessionUtil).addSuccessMessage("cube.save.ok");
    }

    @Test
    public void shouldShowMessageErrorOnSaveCube() {
        cubeHandler.newInstance();
        doThrow(new RuntimeException()).when(cubeManager).save(cubeHandler.getSelected());
        cubeHandler.save(cubeHandler.getSelected());
        verify(sessionUtil).addErrorMessage("cube.save.error");
    }

    @Test
    public void shouldRemoveCube() {
        Cube existentCube = new Cube();
        cubeHandler.delete(existentCube);
        verify(cubeManager).delete(existentCube);
        verify(sessionUtil).addSuccessMessage("cube.delete.ok");
    }

    @Test
    public void shouldShowMessageErrorOnRemoveCube() {
        Cube existentCube = new Cube();
        cubeHandler.newInstance();
        doThrow(new RuntimeException()).when(cubeManager).delete(existentCube);
        cubeHandler.delete(existentCube);
        verify(sessionUtil).addErrorMessage("cube.delete.error");
    }
}
