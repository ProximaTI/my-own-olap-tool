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
import br.com.proximati.biprime.metadata.entity.Filter;

public class FilterHandlerTest {

    private FilterHandler filterHandler;
    private List<Cube> listOfCubes;
    private List<Filter> listOfFilters;
    @Mock
    private SessionUtil sessionUtil;
    @Mock
    private CubeManager cubeManager;

    @Before
    public void setup() {
        initMocks(this);
        filterHandler = new FilterHandler(sessionUtil, cubeManager);
        listOfFilters = new ArrayList<Filter>();
        when(cubeManager.retriveAllCubes()).thenReturn(listOfCubes);
        when(cubeManager.retriveAllFilters()).thenReturn(listOfFilters);
    }

    @Test
    public void shouldLoadFilterListOnInitialize() {
        filterHandler.list();
        verify(cubeManager).retriveAllFilters();
        assertThat(filterHandler.getList(), is(listOfFilters));
    }

    @Test
    public void shouldMessageErrorOnDontRetriveListFilters() {
        when(cubeManager.retriveAllFilters()).thenThrow(new RuntimeException());
        filterHandler.list();
        verify(sessionUtil).addErrorMessage("filter.list.error");
    }

    @Test
    public void shouldEditFilter() {
        filterHandler.list();
        Filter filter = new Filter();
        filterHandler.edit(filter);
        verify(cubeManager).retriveFilter(filter.getId());
    }

    @Test
    public void shouldLoadListOfCubesOnEdit() {
        filterHandler.newInstance();
        assertThat(filterHandler.getListOfCubes(), is(listOfCubes));
    }

    @Test
    public void shouldLoadListOfCubesOnNewInstance() {
        filterHandler.edit(new Filter());
        assertThat(filterHandler.getListOfCubes(), is(listOfCubes));
    }

    @Test
    public void shouldErrorMessageOnEditFilterWhenErrorService() {
        Filter filter = new Filter();
        when(cubeManager.retriveFilter(filter.getId())).thenThrow(
                new RuntimeException());
        filterHandler.list();
        filterHandler.edit(filter);
        verify(sessionUtil).addErrorMessage("filter.edit.error");
    }

    @Test
    public void shouldCreateNewFilter() {
        filterHandler.list();
        filterHandler.newInstance();
        assertThat(filterHandler.getSelected(), is(notNullValue()));
    }

    @Test
    public void shouldSaveFilter() {
        filterHandler.newInstance();
        Filter filter = filterHandler.getSelected();
        filterHandler.save(filter);
        verify(cubeManager).save(filter);

        verify(sessionUtil).addSuccessMessage("filter.save.ok");
    }

    @Test
    public void shouldShowMessageErrorOnSaveFilter() {
        filterHandler.newInstance();
        doThrow(new RuntimeException()).when(cubeManager).save(
                filterHandler.getSelected());
        filterHandler.save(filterHandler.getSelected());
        verify(sessionUtil).addErrorMessage("filter.save.error");
    }

    @Test
    public void shouldRemoveFilter() {
        Filter existentFilter = new Filter();
        filterHandler.delete(existentFilter);
        verify(cubeManager).delete(existentFilter);
        verify(sessionUtil).addSuccessMessage("filter.delete.ok");
    }

    @Test
    public void shouldShowMessageErrorOnRemoveFilter() {
        Filter existentFilter = new Filter();
        filterHandler.newInstance();
        doThrow(new RuntimeException()).when(cubeManager).delete(
                existentFilter);
        filterHandler.delete(existentFilter);
        verify(sessionUtil).addErrorMessage("filter.delete.error");
    }
}
