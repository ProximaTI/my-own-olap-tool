/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model;

import br.com.bi.model.dao.CuboDao;
import br.com.bi.model.dao.DimensionDao;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.query.Query;
import br.com.bi.model.query.sql.QueryTranslator;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class MetadataFacade {

    private static final String CUBE_DAO = "cubeDao";
    private static final String DIMENSION_DAO = "dimensionDao";
    private static MetadataFacade instance;

    private MetadataFacade() {
    }

    public static MetadataFacade getInstance() {
        if (instance == null) {
            instance = new MetadataFacade();
        }
        return instance;
    }

    public String traduzirParaSql(Query consulta) {
        QueryTranslator translator = new QueryTranslator(consulta);
        return translator.translate();
    }

    // ============
    // === Cubo ===
    // ============
    /**
     * Salva no banco de dados o cubo informado.
     * @param cube
     */
    public void save(Cube cube) {
        getCubeDao().save(cube);
    }

    /**
     * Retorna referência para um cubo dado o seu identificador.
     * @param id
     * @return
     */
    public Cube findCubeById(int id) {
        return getCubeDao().findById(id);
    }

    /**
     * Retorna referência para todos os cubos.
     * @return
     */
    public List<Cube> findAllCubos() {
        return getCubeDao().findAll();
    }

    /**
     * Apagar um cubo do banco de dados.
     * @param id
     */
    public void deleteCube(int id) {
        getCubeDao().delete(id);
    }
    // ================
    // === Dimensão ===
    // ================

    public void save(Dimension dimension) {
        getDimensionDao().salvar(dimension);
    }

    public Dimension findDimensionById(int id) {
        return getDimensionDao().findById(id);
    }

    public List<Dimension> findAllDimensions() {
        return getDimensionDao().findAll();
    }

    public void deleteDimension(int id) {
        getDimensionDao().delete(id);
    }

    public List<Level> lowerLevels(int id) {
        return getDimensionDao().lowerLevels(id);
    }

    public Dimension findByLevelId(int id) {
        return getDimensionDao().findByLevelId(id);
    }

    // ================================
    private CuboDao getCubeDao() {
        return (CuboDao) Application.getContext().getBean(CUBE_DAO);
    }

    private DimensionDao getDimensionDao() {
        return (DimensionDao) Application.getContext().getBean(DIMENSION_DAO);
    }
}
