package br.com.bi.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Property;
import br.com.bi.service.CubeManager;
import br.com.bi.service.DimensionManager;
import br.com.bi.ui.util.CrudHandler;
import br.com.bi.ui.util.DefaultCrudListener;
import br.com.bi.ui.util.SessionUtil;

/**
 * Simple controller manage dimensions in view.
 * 
 * @author carlos
 * 
 */
@Component("dimensionHandler")
@Scope("view")
public class DimensionHandler {

    private Logger logger = LoggerFactory.getLogger(DimensionHandler.class);
    private SessionUtil sessionUtil;
    private DimensionManager dimensionManager;
    private CrudHandler<Dimension> viewDimensions;
    private CrudHandler<Level> viewLevels;
    private CrudHandler<CubeLevel> viewCubeLevels;
    private CrudHandler<Property> viewProperties;
    private List<Cube> listOfCubes;
    private CubeManager cubeManager;

    public DimensionHandler() {
    }

    @Autowired
    public DimensionHandler(SessionUtil sessionUtil,
            DimensionManager dimensionManager, CubeManager cubeManager) {
        this.sessionUtil = sessionUtil;
        this.dimensionManager = dimensionManager;
        this.cubeManager = cubeManager;
    }

    @PostConstruct
    public void initialize() {
        viewDimensions = new CrudHandler<Dimension>(
                new DefaultCrudListener<Dimension>() {

                    @Override
                    public Dimension onNewInstance() {
                        return new Dimension();
                    }

                    @Override
                    public List<Dimension> onList() {
                        List<Dimension> list = new ArrayList<Dimension>();
                        try {
                            list = dimensionManager.retriveAllDimensions();
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("dimension.list.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        return list;
                    }

                    @Override
                    public Dimension onEdit(Dimension dimension) {
                        try {
                            getViewDimensions().setSelected(dimensionManager.retriveDimension(dimension.getId()));
                            getViewLevels().list();
                            return dimension;
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("dimension.edit.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onDelete(Dimension dimension) {
                        try {
                            dimensionManager.delete(dimension);
                            sessionUtil.addSuccessMessage("dimension.delete.ok");
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("dimension.delete.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onSave(Dimension dimension) {
                        try {
                            dimensionManager.save(dimension);
                            sessionUtil.addSuccessMessage("dimension.save.ok");
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("dimension.save.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                    }
                });

        viewLevels = new CrudHandler<Level>(new DefaultCrudListener<Level>() {

            @Override
            public Level onNewInstance() {
                Level level = new Level();
                level.setDimension(getViewDimensions().getSelected());
                return level;
            }

            @Override
            public List<Level> onList() {
                List<Level> list = new ArrayList<Level>();
                try {
                    list = dimensionManager.retriveLevelsOfDimension(getViewDimensions().getSelected());
                } catch (Exception e) {
                    sessionUtil.addErrorMessage("level.list.error");
                    logger.error("error", e);
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            public Level onEdit(Level level) {
                try {
                    getViewLevels().setSelected(dimensionManager.retriveLevel(level.getId()));

                    getViewCubeLevels().list();
                    getViewProperties().list();

                    return level;
                } catch (Exception e) {
                    sessionUtil.addErrorMessage("level.edit.error");
                    logger.error("error", e);
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onDelete(Level level) {
                try {
                    dimensionManager.delete(level);
                    sessionUtil.addSuccessMessage("level.delete.ok");
                } catch (Exception e) {
                    sessionUtil.addErrorMessage("level.delete.error");
                    logger.error("error", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onSave(Level level) {
                try {
                    dimensionManager.save(level);
                    sessionUtil.addSuccessMessage("level.save.ok");
                } catch (Exception e) {
                    sessionUtil.addErrorMessage("level.save.error");
                    logger.error("error", e);
                    e.printStackTrace();
                }
            }
        });

        viewCubeLevels = new CrudHandler<CubeLevel>(
                new DefaultCrudListener<CubeLevel>() {

                    @Override
                    public CubeLevel onNewInstance() {
                        try {
                            listOfCubes = cubeManager.retriveAllCubes();
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("cube.list.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        CubeLevel cubeLevel = new CubeLevel();
                        cubeLevel.setLevel(getViewLevels().getSelected());
                        return cubeLevel;
                    }

                    @Override
                    public List<CubeLevel> onList() {
                        List<CubeLevel> list = new ArrayList<CubeLevel>();
                        try {
                            list = dimensionManager.retriveCubeLevelsOfLevel(getViewLevels().getSelected());
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("cubeLevel.list.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        return list;
                    }

                    @Override
                    public CubeLevel onEdit(CubeLevel cubeLevel) {
                        try {
                            listOfCubes = cubeManager.retriveAllCubes();
                            return dimensionManager.retriveCubeLevel(cubeLevel.getId());
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("cubeLevel.edit.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onDelete(CubeLevel cubeLevel) {
                        try {
                            dimensionManager.delete(cubeLevel);
                            sessionUtil.addSuccessMessage("cubeLevel.delete.ok");
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("cubeLevel.delete.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSave(CubeLevel cubeLevel) {
                        try {
                            dimensionManager.save(cubeLevel);
                            sessionUtil.addSuccessMessage("cubeLevel.save.ok");
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("cubeLevel.save.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }

                    }
                });

        viewProperties = new CrudHandler<Property>(
                new DefaultCrudListener<Property>() {

                    @Override
                    public Property onNewInstance() {
                        Property property = new Property();
                        property.setLevel(getViewLevels().getSelected());
                        return property;
                    }

                    @Override
                    public List<Property> onList() {
                        List<Property> list = new ArrayList<Property>();
                        try {
                            list = dimensionManager.retrivePropertiesOfLevel(getViewLevels().getSelected());
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("property.list.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        return list;
                    }

                    @Override
                    public Property onEdit(Property property) {
                        try {
                            return dimensionManager.retriveProperty(property.getId());
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("property.edit.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    public void onDelete(Property property) {
                        try {
                            dimensionManager.delete(property);
                            sessionUtil.addSuccessMessage("property.delete.ok");
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("property.delete.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSave(Property property) {
                        try {
                            dimensionManager.save(property);
                            sessionUtil.addSuccessMessage("property.save.ok");
                        } catch (Exception e) {
                            sessionUtil.addErrorMessage("property.save.error");
                            logger.error("error", e);
                            e.printStackTrace();
                        }

                    }
                });

        viewDimensions.list();
    }

    public CrudHandler<Dimension> getViewDimensions() {
        return viewDimensions;
    }

    public CrudHandler<Level> getViewLevels() {
        return viewLevels;
    }

    public CrudHandler<CubeLevel> getViewCubeLevels() {
        return viewCubeLevels;
    }

    public CrudHandler<Property> getViewProperties() {
        return viewProperties;
    }

    public List<Cube> getListOfCubes() {
        return listOfCubes;
    }
}
