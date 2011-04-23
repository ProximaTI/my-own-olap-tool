package br.com.bi.ui;

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

import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.service.CubeManager;
import br.com.bi.ui.util.SessionUtil;

public class MeasureHandlerTest {

	private MeasureHandler measureHandler;
	private List<Cube> listOfCubes;
	private List<Measure> listOfMeasures;
	@Mock
	private SessionUtil sessionUtil;
	@Mock
	private CubeManager cubeManager;

	@Before
	public void setup() {
		initMocks(this);
		measureHandler = new MeasureHandler(sessionUtil, cubeManager);
		listOfMeasures = new ArrayList<Measure>();
		when(cubeManager.retriveAllCubes()).thenReturn(listOfCubes);
		when(cubeManager.retriveAllMeasures()).thenReturn(listOfMeasures);
	}

	@Test
	public void shouldLoadMeasureListOnInitialize() {
		measureHandler.list();
		verify(cubeManager).retriveAllMeasures();
		assertThat(measureHandler.getList(), is(listOfMeasures));
	}

	@Test
	public void shouldMessageErrorOnDontRetriveListMeasures() {
		when(cubeManager.retriveAllMeasures())
				.thenThrow(new RuntimeException());
		measureHandler.list();
		verify(sessionUtil).addErrorMessage("measure.list.error");
	}

	@Test
	public void shouldEditMeasure() {
		measureHandler.list();
		Measure measure = new Measure();
		measureHandler.edit(measure);
		verify(cubeManager).retriveMeasure(measure.getId());
	}

	@Test
	public void shouldLoadListOfCubesOnEdit() {
		measureHandler.newInstance();
		assertThat(measureHandler.getListOfCubes(), is(listOfCubes));
	}

	@Test
	public void shouldLoadListOfCubesOnNewInstance() {
		measureHandler.edit(new Measure());
		assertThat(measureHandler.getListOfCubes(), is(listOfCubes));
	}

	@Test
	public void shouldErrorMessageOnEditMeasureWhenErrorService() {
		Measure measure = new Measure();
		when(cubeManager.retriveMeasure(measure.getId())).thenThrow(
				new RuntimeException());
		measureHandler.list();
		measureHandler.edit(measure);
		verify(sessionUtil).addErrorMessage("measure.edit.error");
	}

	@Test
	public void shouldCreateNewMeasure() {
		measureHandler.list();
		measureHandler.newInstance();
		assertThat(measureHandler.getSelected(), is(notNullValue()));
	}

	@Test
	public void shouldSaveMeasure() {
		measureHandler.newInstance();
		Measure measure = measureHandler.getSelected();
		measureHandler.save(measure);
		verify(cubeManager).save(measure);

		verify(sessionUtil).addSuccessMessage("measure.save.ok");
	}

	@Test
	public void shouldShowMessageErrorOnSaveMeasure() {
		measureHandler.newInstance();
		doThrow(new RuntimeException()).when(cubeManager).save(
				measureHandler.getSelected());
		measureHandler.save(measureHandler.getSelected());
		verify(sessionUtil).addErrorMessage("measure.save.error");
	}

	@Test
	public void shouldRemoveMeasure() {
		Measure existentMeasure = new Measure();
		measureHandler.delete(existentMeasure);
		verify(cubeManager).delete(existentMeasure);
		verify(sessionUtil).addSuccessMessage("measure.delete.ok");
	}

	@Test
	public void shouldShowMessageErrorOnRemoveMeasure() {
		Measure existentMeasure = new Measure();
		measureHandler.newInstance();
		doThrow(new RuntimeException()).when(cubeManager).delete(
				existentMeasure);
		measureHandler.delete(existentMeasure);
		verify(sessionUtil).addErrorMessage("measure.delete.error");
	}
}
