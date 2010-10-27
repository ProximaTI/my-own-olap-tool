/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;


import br.com.bi.model.dao.DimensionDao;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Property;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Luiz
 */
public class DimensaoDaoJdbc extends AbstractDaoJdbc implements DimensionDao {

    public List<Dimension> findAll() {
        return getJdbcTemplate().query("select * from dimensao",
                                       new DimensaoShallowMapper());
    }

    public Level findLevelById(int id) {
        return getJdbcTemplate().queryForObject("select * from nivel where id = ?",
                                                new Object[] { id },
                                                new LevelMapper());
    }

    public Dimension findById(int id) {
        return getJdbcTemplate().queryForObject("select * from dimensao where id = :id",
                                                new Object[] { id },
                                                new DimensaoDeepMapper());
    }

    public void salvar(Dimension dimensao) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("nome", dimensao.getName());
        parameters.put("descricao", dimensao.getDescription());

        if (!dimensao.isPersisted()) {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
            insert.withTableName("dimensao").usingGeneratedKeyColumns("id");
            dimensao.setId(insert.executeAndReturnKey(parameters).intValue());
        } else {
            SqlUpdate update =
                new SqlUpdate(getDataSource(), "update dimensao set nome = :nome, " +
                              "descricao = :descricao where id = :id");
            parameters.put("id", dimensao.getId());
            update.updateByNamedParam(parameters);
        }

        salvarNiveis(dimensao);
    }

    /**
     * Persiste os níveis de uma dimensão.
     * @param dimensao
     */
    @Transactional
    private void salvarNiveis(Dimension dimensao) {
        List<Integer> levels = new ArrayList<Integer>();

        for (Level level : dimensao.getLevels()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("iddimensao", dimensao.getId());
            parameters.put("nome", level.getName());
            parameters.put("descricao", level.getDescription());
            parameters.put("esquema", level.getSchema());
            parameters.put("tabela", level.getTable());
            parameters.put("juncaoNivelSuperior",
                           level.getJoinColumnUpperLevel());
            parameters.put("indice", level.getIndex());

            if (!level.isPersisted()) {
                SimpleJdbcInsert insert =
                    new SimpleJdbcInsert(getDataSource());
                insert.withTableName("nivel").usingGeneratedKeyColumns("id");
                level.setId(insert.executeAndReturnKey(parameters).intValue());
            } else {
                SqlUpdate update =
                    new SqlUpdate(getDataSource(), "update nivel set nome = :nome, " +
                                  "descricao = :descricao, esquema = :esquema, tabela = :tabela, " +
                                  "juncaoNivelSuperior = :juncaoNivelSuperior, indice = :indice " +
                                  "where id = :id");
                parameters.put("id", level.getId());
                update.updateByNamedParam(parameters);
            }

            saveProperties(level);

            levels.add(level.getId());
        }

        if (levels.size() > 0) {
            getJdbcTemplate().update("delete from nivel " +
                                     "where not id in (:ids) and iddimensao = :iddimensao".replace(":ids",
                                                                                                   listToString(levels)).replace(":iddimensao",
                                                                                                                                 Integer.toString(dimensao.getId())));
        }
    }

    @Transactional
    public void delete(int idDimensao) {
        deleteLevels(findById(idDimensao));

        getJdbcTemplate().update("delete from dimensao where id = ?",
                                 new Object[] { idDimensao });
    }

    /**
     * Apaga os níveis de uma dimensão.
     * @param dimension
     */
    @Transactional
    private void deleteLevels(Dimension dimension) {
        for (Level level : dimension.getLevels()) {
            deleteProperties(level);
        }

        getJdbcTemplate().update("delete from nivel where iddimensao = ?",
                                 new Object[] { dimension.getId() });
    }

    /**
     * Apagar as propriedades de nível.
     * @param level
     */
    @Transactional
    private void deleteProperties(Level level) {
        getJdbcTemplate().update("delete from propriedade where idnivel = ?",
                                 new Object[] { level.getId() });
    }

    /**
     * Persiste as propriedades de um nível.
     * @param level
     */
    @Transactional
    private void saveProperties(Level level) {
        List properties = new ArrayList();

        for (Property propriedade : level.getProperties()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idnivel", level.getId());
            parameters.put("nome", propriedade.getName());
            parameters.put("descricao", propriedade.getDescription());
            parameters.put("coluna", propriedade.getColumn());
            parameters.put("propriedadeCodigo",
                           propriedade.isCodeProperty() ? 1 : 0);
            parameters.put("propriedadeNome",
                           propriedade.isNameProperty() ? 1 : 0);

            if (!propriedade.isPersisted()) {
                SimpleJdbcInsert insert =
                    new SimpleJdbcInsert(getDataSource());
                insert.withTableName("propriedade").usingGeneratedKeyColumns("id");
                propriedade.setId(insert.executeAndReturnKey(parameters).intValue());
            } else {
                SqlUpdate update =
                    new SqlUpdate(getDataSource(), "update propriedade set nome = :nome, " +
                                  "descricao = :descricao, coluna = :coluna, propriedadeCodigo = :propriedadeCodigo, " +
                                  "propriedadeNome = :propriedadeNome where id = :id");
                parameters.put("id", propriedade.getId());
                update.updateByNamedParam(parameters);
            }

            properties.add(propriedade.getId());
        }

        if (properties.size() > 0) {
            getJdbcTemplate().update("delete from propriedade where not id in (:ids) and idnivel = :idnivel".replace(":ids",
                                                                                                                     listToString(properties)).replace(":idnivel",
                                                                                                                                                       Integer.toString(level.getId())));
        }
    }

    /**
     * Retorna a lista de níveis dado o identificador da dimensão.
     * @param id
     * @return
     */
    private List<Level> findLevelsByDimensionId(int id) {
        return getJdbcTemplate().query("select * from nivel where iddimensao = ?",
                                       new Object[] { id }, new LevelMapper());
    }

    /**
     * Retorna as propriedades de nível dado seu identificador.
     * @param id
     * @return
     */
    private List<Property> findPropertiesByLevel(int id) {
        return getJdbcTemplate().query("select * from propriedade where idnivel = ?",
                                       new Object[] { id },
                                       new RowMapper<Property>() {

                public Property mapRow(ResultSet rs,
                                       int i) throws SQLException {
                    Property property = new Property();

                    property.setColuna(rs.getString("coluna"));
                    property.setDescription(rs.getString("descricao"));
                    property.setId(rs.getInt("id"));
                    property.setName(rs.getString("nome"));
                    property.setPropriedadeCodigo(rs.getInt("propriedadeCodigo") ==
                                                  1);
                    property.setPropriedadeNome(rs.getInt("propriedadeNome") ==
                                                1);
                    property.setPersisted(true);

                    return property;
                }
            });
    }

    /**
     * Retorna os níveis que estão hierarquicamente abaixo do nível denotado por idnivel,
     * inclusive o próprio.
     * @param idnivel
     * @return
     */
    public List<Level> lowerLevels(int idnivel) {
        return getJdbcTemplate().query("select a.* from nivel a, nivel b " +
                                       "where a.iddimensao = b.iddimensao " +
                                       "and a.indice >= b.indice and b.id = ? order by a.indice",
                                       new Object[] { idnivel },
                                       new LevelMapper());
    }

    /**
     * Retorna a dimensão à qual pertence o nível denotado por idnivel.
     * @param idnivel
     * @return
     */
    public Dimension findByLevelId(int idnivel) {
        return getJdbcTemplate().queryForObject("select a.* from dimensao a, nivel b " +
                                                "where a.id = b.iddimensao and b.id = ?",
                                                new Object[] { idnivel },
                                                new DimensaoDeepMapper());
    }

    public List<Level> findAllLevels() {
        return getJdbcTemplate().query("select * from nivel order by nome",
                                       new LevelMapper());
    }

    class DimensaoShallowMapper implements RowMapper<Dimension> {

        public Dimension mapRow(ResultSet rs, int i) throws SQLException {
            Dimension dimension = new Dimension();

            dimension.setDescription(rs.getString("descricao"));
            dimension.setId(rs.getInt("id"));
            dimension.setName(rs.getString("nome"));
            dimension.setPersisted(true);

            return dimension;
        }
    }

    class DimensaoDeepMapper extends DimensaoShallowMapper {

        @Override
        public Dimension mapRow(ResultSet rs, int i) throws SQLException {
            Dimension dimension = super.mapRow(rs, i);

            dimension.setLevels(findLevelsByDimensionId(rs.getInt("id")));

            return dimension;
        }
    }

    class LevelMapper implements RowMapper<Level> {

        public Level mapRow(ResultSet rs, int i) throws SQLException {
            Level level = new Level();
            level.setDescription(rs.getString("descricao"));
            level.setSchema(rs.getString("esquema"));
            level.setId(rs.getInt("id"));
            level.setJoinColumnUpperLevel(rs.getString("juncaoNivelSuperior"));
            level.setName(rs.getString("nome"));
            level.setProperties(findPropertiesByLevel(level.getId()));
            level.setTable(rs.getString("tabela"));
            level.setIndex(rs.getInt("indice"));
            level.setPersisted(true);
            return level;
        }
    }
}
