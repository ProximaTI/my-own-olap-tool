package br.com.proximati.biprime.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.proximati.biprime.service.CubeManager;
import br.com.proximati.biprime.ui.util.SessionUtil;
import br.com.proximati.biprime.metadata.entity.Cube;

/**
 * Simple controller manage cubes in view.
 * 
 * support basic operations of manage cube.
 * 
 * @author carlos
 * 
 */
@Component("cubeHandler")
@Scope("view")
public class CubeHandler {

    private static final Logger logger = LoggerFactory.getLogger(CubeHandler.class);
    private SessionUtil sessionUtil;
    private List<Cube> list;
    private Cube selected;
    private CubeManager cubeManager;

    public CubeHandler() {
    }

    @Autowired
    public CubeHandler(SessionUtil sessionUtil, CubeManager cubeManager) {
        this.sessionUtil = sessionUtil;
        this.cubeManager = cubeManager;
    }

    @PostConstruct
    public void list() {
        try {
            list = new ArrayList<Cube>();
            list = cubeManager.retriveAllCubes();
            cancelEdit();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("cube.list.error");
            logger.error("error", e);
        }
    }

    public void newInstance() {
        selected = new Cube();
    }

    public void edit(Cube cube) {
        try {
            selected = cubeManager.retriveCube(cube.getId());
            if (selected == null) {
                list();
            }
        } catch (Exception e) {
            sessionUtil.addErrorMessage("cube.edit.error");
            logger.error("error", e);
            list();
        }
    }

    public void save(Cube cube) {
        try {
            cubeManager.save(cube);
            sessionUtil.addSuccessMessage("cube.save.ok");
            list();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("cube.save.error");
        }
    }

    public void delete(Cube cube) {
        try {
            cubeManager.delete(cube);
            sessionUtil.addSuccessMessage("cube.delete.ok");
            list();
        } catch (Exception e) {
            sessionUtil.addErrorMessage("cube.delete.error");
            logger.error("error", e);
        }
    }

    public void cancelEdit() {
        selected = null;
    }

    public Cube getSelected() {
        return selected;
    }

    public List<Cube> getList() {
        return list;
    }
}
