package br.com.bi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bi.model.dao.CubeDao;
import br.com.bi.model.dao.FilterDao;
import br.com.bi.model.dao.MeasureDao;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.model.entity.metadata.Measure;

/**
 * service layer of manager cube data relations managed cubes,measures,filters.
 * 
 * @author carlos
 *
 */
@Service
public class CubeManager {

    private CubeDao cubeDao;
    private MeasureDao measureDao;
    private FilterDao filterDao;

    public CubeManager() {
    }

    @Autowired
    public CubeManager(CubeDao cubeDao, MeasureDao measureDao, FilterDao filterDao) {
        this.cubeDao = cubeDao;
        this.measureDao = measureDao;
        this.filterDao = filterDao;
    }

    public Cube retriveCube(Integer id) {
        return cubeDao.findOne(id);
    }

    public List<Cube> retriveAllCubes() {
        List<Cube> cubes = new ArrayList<Cube>();

        for (Cube cube : cubeDao.findAll()) {
            cubes.add(cube);
        }
        return cubes;
    }

    @Transactional
    public void delete(Cube cube) {
        cubeDao.delete(cube);
    }

    @Transactional
    public void save(Cube cube) {
        cubeDao.save(cube);
    }

    public Measure retriveMeasure(Integer id) {
        return measureDao.findOne(id);
    }

    public List<Measure> retriveAllMeasures() {
        List<Measure> measures = new ArrayList<Measure>();

        for (Measure measure : measureDao.findAll()) {
            measures.add(measure);
        }

        return measures;
    }

    @Transactional
    public void delete(Measure measure) {
        measureDao.delete(measure);
    }

    @Transactional
    public void save(Measure measure) {
        measureDao.save(measure);
    }

    public Filter retriveFilter(Integer id) {
        return filterDao.findOne(id);
    }

    public List<Filter> retriveAllFilters() {
        List<Filter> filters = new ArrayList<Filter>();

        for (Filter filter : filterDao.findAll()) {
            filters.add(filter);
        }

        return filters;
    }

    @Transactional
    public void delete(Filter filter) {
        filterDao.delete(filter);
    }

    @Transactional
    public void save(Filter filter) {
        filterDao.save(filter);
    }
}
