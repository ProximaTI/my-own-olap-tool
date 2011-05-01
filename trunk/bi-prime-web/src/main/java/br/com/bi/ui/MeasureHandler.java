package br.com.bi.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.service.CubeManager;
import br.com.bi.ui.util.SessionUtil;

/**
 * simple crud of manages measures
 * 
 * @author carlos
 *
 */
@Controller("measureHandler")
@Scope("view")
public class MeasureHandler {

    private Logger logger = LoggerFactory.getLogger(MeasureHandler.class);
    private SessionUtil sessionUtil;
    private List<Measure> list;
    private Measure selected;
    private CubeManager cubeManager;
    private List<Cube> listOfCubes;

    public MeasureHandler() {
    }

    @Autowired
    public MeasureHandler(SessionUtil sessionUtil, CubeManager cubeManager) {
        this.sessionUtil = sessionUtil;
        this.cubeManager = cubeManager;
    }

    @PostConstruct
    public void list() {
        try {
            list = new ArrayList<Measure>();
            list = cubeManager.retriveAllMeasures();
            cancelEdit();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("measure.list.error");
            logger.error("error", e);
        }
    }

    public void newInstance() {
        selected = new Measure();
        loadCubes();
    }

    public void edit(Measure measure) {
        try {
            selected = cubeManager.retriveMeasure(measure.getId());
            if (selected == null) {
                list();
            }
            loadCubes();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("measure.edit.error");
            logger.error("error", e);
            list();
        }
    }

    public void loadCubes() {
        try {
            listOfCubes = new ArrayList<Cube>();
            listOfCubes = cubeManager.retriveAllCubes();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("cube.list.error");
            logger.error("error", e);
        }

    }

    public void save(Measure measure) {
        try {
            cubeManager.save(measure);
            sessionUtil.addSuccessMessage("measure.save.ok");
            list();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("measure.save.error");
            logger.error("error", e);
        }
    }

    public void delete(Measure measure) {
        try {
            cubeManager.delete(measure);
            sessionUtil.addSuccessMessage("measure.delete.ok");
            list();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("measure.delete.error");
            logger.error("error", e);
        }
    }

    public void cancelEdit() {
        selected = null;
    }

    public Measure getSelected() {
        return selected;
    }

    public List<Measure> getList() {
        return list;
    }

    public List<Cube> getListOfCubes() {
        return listOfCubes;
    }
}
