package br.com.proximati.biprime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.proximati.biprime.metadata.dao.CubeLevelDao;
import br.com.proximati.biprime.metadata.dao.DimensionDao;
import br.com.proximati.biprime.metadata.dao.LevelDao;
import br.com.proximati.biprime.metadata.dao.PropertyDao;
import br.com.proximati.biprime.metadata.entity.CubeLevel;
import br.com.proximati.biprime.metadata.entity.Dimension;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Property;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * class manage dimensions and leafs of dimensions (level, cubeLevel, property)
 * 
 * @author carlos
 *
 */
@Service
public class DimensionManager {

    private DimensionDao dimensionDao;
    private LevelDao levelDao;
    private CubeLevelDao cubeLevelDao;
    private PropertyDao propertyDao;

    public DimensionManager() {
    }

    @Autowired
    public DimensionManager(DimensionDao dimensionDao, LevelDao levelDao,
            CubeLevelDao cubeLevelDao, PropertyDao propertyDao) {
        this.dimensionDao = dimensionDao;
        this.levelDao = levelDao;
        this.cubeLevelDao = cubeLevelDao;
        this.propertyDao = propertyDao;
    }

    public Dimension retriveDimension(Integer id) {
        return dimensionDao.findOne(id);
    }

    public List<Dimension> retriveAllDimensions() {
        List<Dimension> dimensions = new ArrayList<Dimension>();

        for (Dimension dimension : dimensionDao.findAll()) {
            dimensions.add(dimension);
        }
        return dimensions;
    }

    @Transactional
    public void delete(Dimension dimension) {
        dimensionDao.delete(dimension);
    }

    @Transactional
    public void save(Dimension dimension) {
        dimensionDao.save(dimension);
    }

    public List<Level> retriveLevelsOfDimension(Dimension dimension) {
        return levelDao.findByDimension(dimension);
    }

    public Level retriveLevel(Integer id) {
        return levelDao.findOne(id);
    }

    public List<Level> retriveAllLevels() {
        List<Level> levels = new ArrayList<Level>();

        for (Level level : levelDao.findAll()) {
            levels.add(level);
        }
        return levels;
    }

    @Transactional
    public void delete(Level level) {
        levelDao.delete(level);
    }

    @Transactional
    public void save(Level level) {
        levelDao.save(level);
    }

    public CubeLevel retriveCubeLevel(Integer id) {
        return cubeLevelDao.findOne(id);
    }

    public List<CubeLevel> retriveCubeLevelsOfLevel(Level level) {
        return cubeLevelDao.findByLevel(level);
    }

    public List<CubeLevel> retriveAllCubeLevels() {
        List<CubeLevel> cubeLevels = new ArrayList<CubeLevel>();

        for (CubeLevel cubeLevel : cubeLevelDao.findAll()) {
            cubeLevels.add(cubeLevel);
        }
        return cubeLevels;
    }

    @Transactional
    public void delete(CubeLevel cubeCubeLevel) {
        cubeLevelDao.delete(cubeCubeLevel);
    }

    @Transactional
    public void save(CubeLevel cubeCubeLevel) {
        cubeLevelDao.save(cubeCubeLevel);
    }

    public Property retriveProperty(Integer id) {
        return propertyDao.findOne(id);
    }

    public List<Property> retrivePropertiesOfLevel(Level level) {
        return propertyDao.findByLevel(level);
    }

    public List<Property> retriveAllProperties() {
        List<Property> properties = new ArrayList<Property>();

        for (Property property : propertyDao.findAll()) {
            properties.add(property);
        }
        return properties;
    }

    @Transactional
    public void delete(Property property) {
        propertyDao.delete(property);
    }

    @Transactional
    public void save(Property property) {
        propertyDao.save(property);
    }
}
