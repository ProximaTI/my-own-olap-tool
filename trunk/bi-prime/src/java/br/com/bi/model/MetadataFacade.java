/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model;


import br.com.bi.model.dao.CubeDao;
import br.com.bi.model.dao.DimensionDao;
import br.com.bi.model.dao.LevelDao;
import br.com.bi.model.driver.RdbmsDriver;
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
    private static final String LEVEL_DAO = "levelDao";
    private static final String DIMENSION_DAO = "dimensionDao";

    private static final String RDBMS_DRIVER = "rdbmsDriver";

    private static MetadataFacade instance;

    private MetadataFacade() {
    }

    public static MetadataFacade getInstance() {
        if (instance == null) {
            instance = new MetadataFacade();
        }
        return instance;
    }

    public String translateToSql(Query query) {
        QueryTranslator translator = new QueryTranslator(query);
        return translator.translate();
    }

    // ========
    // = Cubo =
    // ========

    /**
     * Salva no banco de dados o cubo informado.
     * @param cube
     */
    public void saveCube(Cube cube) {
        getCubeDao().save(cube);
    }

    /**
     * Retorna referência para um cubo dado o seu identificador.
     * @param id
     * @return
     */
    public Cube findCubeById(Integer id) {
        return getCubeDao().findById(id);
    }

    /**
     * Retorna referência para todos os cubos.
     * @return
     */
    public List<Cube> findAllCubes() {
        return getCubeDao().findAll();
    }

    /**
     * Apagar um cubo do banco de dados.
     * @param id
     */
    public void deleteCube(Integer id) {
        getCubeDao().delete(id);
    }

    // ============
    // = Dimensão =
    // ============

    /**
     * Retorna lista com todos as dimensões persistidas.
     * @return
     */
    public List<Dimension> findAllDimensions() {
        return getDimensionDao().findAll();
    }

    /**
     * Retorna referência para uma dimensão dado o seu identificador.
     * @param id
     * @return
     */
    public Dimension findDimensionById(Integer id) {
        return getDimensionDao().findById(id);
    }

    /**
     * Salva uma dimensão no banco de dados.
     * @param dimension
     */
    public void saveDimension(Dimension dimension) {
        getDimensionDao().save(dimension);
    }

    /**
     * Apaga uma dimensão do banco de dados, dado seu identificador.
     * @param id
     */
    public void deleteDimension(Integer id) {
        getDimensionDao().delete(id);
    }

    // =========
    // = Nível =
    // =========

    /**
     * Retorna lista com todos os níveis persistidos.
     * @return
     */
    public List<Level> findAllLevels() {
        return getLevelDao().findAll();
    }

    /**
     * Retorna referência para um nível dado o seu identificador.
     * @param id
     * @return
     */
    public Level findLevelById(Integer id) {
        return getLevelDao().findById(id);
    }

    /**
     * Salva um nível no banco de dados.
     * @param level
     */
    public void saveLevel(Level level) {
        getLevelDao().save(level);
    }

    /**
     * Apaga um nível do banco de dados, dado seu identificador.
     * @param id
     */
    public void deleteLevel(Integer id) {
        getLevelDao().delete(id);
    }

    // ============
    // = Metadata =
    // ============

    /**
     * Retorna todos os esquemas do banco de dados.
     * @return
     */
    public List<String> findAllSchemas() {
        RdbmsDriver driver =
            (RdbmsDriver)Application.getContext().getBean(RDBMS_DRIVER);

        return driver.getSchemas();
    }

    public List<String> findTablesBySchema(String schema) {
        RdbmsDriver driver =
            (RdbmsDriver)Application.getContext().getBean(RDBMS_DRIVER);

        return driver.getTables(schema);
    }

    public List<String> findColumnsByTable(String schema, String table) {
        RdbmsDriver driver =
            (RdbmsDriver)Application.getContext().getBean(RDBMS_DRIVER);

        return driver.getColumns(schema, table);
    }

    // =======================
    // = Data access objects =
    // =======================

    private CubeDao getCubeDao() {
        return (CubeDao)Application.getContext().getBean(CUBE_DAO);
    }

    private LevelDao getLevelDao() {
        return (LevelDao)Application.getContext().getBean(LEVEL_DAO);
    }

    private DimensionDao getDimensionDao() {
        return (DimensionDao)Application.getContext().getBean(DIMENSION_DAO);
    }
}
