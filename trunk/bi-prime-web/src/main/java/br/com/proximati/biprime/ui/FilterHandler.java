package br.com.proximati.biprime.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.proximati.biprime.service.CubeManager;
import br.com.proximati.biprime.ui.util.SessionUtil;
import br.com.proximati.biprime.metadata.entity.Cube;
import br.com.proximati.biprime.metadata.entity.Filter;

/**
 * simple crud of manages Filters
 * 
 * @author carlos
 *
 */
@Controller("filterHandler")
@Scope("view")
public class FilterHandler {

    private Logger logger = LoggerFactory.getLogger(FilterHandler.class);
    private SessionUtil sessionUtil;
    private List<Filter> list;
    private Filter selected;
    private CubeManager cubeManager;
    private List<Cube> listOfCubes;

    public FilterHandler() {
    }

    @Autowired
    public FilterHandler(SessionUtil sessionUtil, CubeManager cubeManager) {
        this.sessionUtil = sessionUtil;
        this.cubeManager = cubeManager;
    }

    @PostConstruct
    public void list() {
        try {
            list = new ArrayList<Filter>();
            list = cubeManager.retriveAllFilters();
            cancelEdit();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("filter.list.error");
            logger.error("error", e);
        }
    }

    public void newInstance() {
        selected = new Filter();
        loadCubes();
    }

    public void edit(Filter filter) {
        try {
            selected = cubeManager.retriveFilter(filter.getId());
            if (selected == null) {
                list();
            }
            loadCubes();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("filter.edit.error");
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

    public void save(Filter filter) {
        try {
            cubeManager.save(filter);
            sessionUtil.addSuccessMessage("filter.save.ok");
            list();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("filter.save.error");
            logger.error("error", e);
        }
    }

    public void delete(Filter filter) {
        try {
            cubeManager.delete(filter);
            sessionUtil.addSuccessMessage("filter.delete.ok");
            list();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("filter.delete.error");
            logger.error("error", e);
        }
    }

    public void cancelEdit() {
        selected = null;
    }

    public Filter getSelected() {
        return selected;
    }

    public List<Filter> getList() {
        return list;
    }

    public List<Cube> getListOfCubes() {
        return listOfCubes;
    }
}
