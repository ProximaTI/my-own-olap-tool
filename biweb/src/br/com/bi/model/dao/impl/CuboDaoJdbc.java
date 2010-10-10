/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.CuboDao;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.model.entity.metadata.Level;
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
public class CuboDaoJdbc extends AbstractDaoJdbc implements CuboDao {

    /**
     * Retorna todos os cubos cadastrados (carga rasa).
     * @return
     */
    public List<Cube> findAll() {
        return getJdbcTemplate().query("select * from cubo order by nome", new CuboShallowMapper());
    }

    /**
     * Retorna um cubo dado o seu identificador (carga profunda).
     * @param id
     * @return
     */
    public Cube findById(int id) {
        return getJdbcTemplate().queryForObject("select * from cubo where id = ?", new Object[]{
                    id}, new CuboDeepMapper());
    }

    /**
     * Salva um cubo no banco de dados.
     * @param cubo
     */
    @Transactional
    public void save(Cube cubo) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("nome", cubo.getName());
        parameters.put("descricao", cubo.getDescription());
        parameters.put("esquema", cubo.getSchema());
        parameters.put("tabela", cubo.getTable());

        if (!cubo.isPersisted()) {
            SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
            insert.withTableName("cubo").usingGeneratedKeyColumns("id");
            cubo.setId(insert.executeAndReturnKey(parameters).intValue());
        } else {
            SqlUpdate update =
                    new SqlUpdate(getDataSource(), "update cubo set nome = :nome, "
                    + "descricao = :descricao, esquema = :esquema, tabela = :tabela "
                    + "where id = :id");
            parameters.put("id", cubo.getId());
            update.updateByNamedParam(parameters);
        }

        salvarMetricas(cubo);
        salvarFiltros(cubo);
        salvarNiveis(cubo);
    }

    /**
     * Persiste as métricas de um cubo.
     * @param cubo
     */
    @Transactional
    private void salvarMetricas(Cube cubo) {
        List metricas = new ArrayList();

        for (Measure metrica : cubo.getMeasures()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("nome", metrica.getName());
            parameters.put("descricao", metrica.getDescription());
            parameters.put("funcao", metrica.getCodigoFuncao());
            parameters.put("coluna", metrica.getColumn());
            parameters.put("expressaoFiltro", metrica.getFilterExpression());
            parameters.put("metricaPadrao", metrica.isDefaultMeasure() ? 1 : 0);

            if (!metrica.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("metrica").usingGeneratedKeyColumns("id");
                metrica.setId(insert.executeAndReturnKey(parameters).intValue());
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update metrica set nome = :nome, "
                        + "descricao = :descricao, funcao = :funcao, coluna = :coluna,"
                        + "expressaoFiltro = :expressaoFiltro, metricaPadrao = :metricaPadrao "
                        + "where id = :id");
                parameters.put("id", metrica.getId());
                update.updateByNamedParam(parameters);
            }

            metricas.add(metrica.getId());
        }

        if (metricas.size() > 0) {
            getJdbcTemplate().update("delete from metrica where not id in (:ids) and idcubo = :idcubo".
                    replace(":ids", listToString(metricas)).replace(":idcubo", Integer.
                    toString(cubo.getId())));
        }
    }

    /**
     * Persiste os filtros de um cubo.
     * @param cubo
     */
    @Transactional
    private void salvarFiltros(Cube cubo) {
        List filtros = new ArrayList();

        for (Filter filtro : cubo.getFilters()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("nome", filtro.getName());
            parameters.put("descricao", filtro.getDescription());
            parameters.put("expressao", filtro.getExpression());

            if (!filtro.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("filtro").usingGeneratedKeyColumns("id");
                filtro.setId(insert.executeAndReturnKey(parameters).intValue());
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update filtro set nome = :nome, "
                        + "descricao = :descricao, expressao = :expressao where id = :id");
                parameters.put("id", filtro.getId());
                update.updateByNamedParam(parameters);
            }

            filtros.add(filtro.getId());
        }

        if (filtros.size() > 0) {
            getJdbcTemplate().update("delete from filtro where not id in (:ids) and idcubo = :idcubo".
                    replace(":ids", listToString(filtros)).replace(":idcubo", Integer.
                    toString(cubo.getId())));
        }
    }

    /**
     * Persiste os níveis de um cubo.
     * @param cubo
     */
    @Transactional
    private void salvarNiveis(Cube cubo) {
        List niveis = new ArrayList();

        for (CubeLevel nivel : cubo.getCubeLevels()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("idnivel", nivel.getLevel().getId());
            parameters.put("colunaJuncao", nivel.getJoinColumn());

            if (!nivel.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("cubo_nivel");
                insert.execute(parameters);
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update cubo_nivel set colunaJuncao = :colunaJuncao"
                        + " where idcubo = :id and idnivel = :idnivel");
                update.updateByNamedParam(parameters);
            }

            niveis.add(nivel.getLevel().getId());
        }

        if (niveis.size() > 0) {
            getJdbcTemplate().update("delete from cubo_nivel "
                    + "where not idnivel in (:ids) and idcubo = :idcubo".replace(":ids", listToString(niveis)).
                    replace(":idcubo", Integer.toString(cubo.getId())));
        }
    }

    /**
     * Apaga do banco de dados um cubo dado o seu identificador.
     * @param id
     */
    @Transactional
    public void delete(int id) {
        // realiza carga completa do cubo
        Cube cubo = findById(id);

        apagarFiltros(cubo.getId());
        apagarMetricas(cubo.getId());
        apagarNiveis(cubo.getId());

        getJdbcTemplate().update("delete from cubo where id = ?", new Object[]{
                    id});
    }

    private List<CubeLevel> findNiveisByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from cubo_nivel where idcubo = ?", new Object[]{
                    idCubo}, new RowMapper<CubeLevel>() {

            public CubeLevel mapRow(ResultSet rs, int i) throws SQLException {
                CubeLevel nivel = new CubeLevel();
                nivel.setLevel(findNivelById(rs.getInt("idnivel")));
                nivel.setJoinColumn(rs.getString("colunaJuncao"));
                return nivel;
            }

            private Level findNivelById(int aInt) {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        });
    }

    /**
     * Retorna os filtros de um cubo dado seu identificador.
     * @param idCubo
     * @return
     */
    private List<Filter> findFiltrosByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from filtro where idcubo = ?", new Object[]{
                    idCubo}, new RowMapper<Filter>() {

            public Filter mapRow(ResultSet rs, int i) throws SQLException {
                Filter filtro = new Filter();

                filtro.setDescription(rs.getString("descricao"));
                filtro.setExpression(rs.getString("expressao"));
                filtro.setId(rs.getInt("id"));
                filtro.setName(rs.getString("nome"));

                return filtro;
            }
        });
    }

    /**
     * Retorna as métricas de cubo dado o seu identificador.
     * @param idCubo
     * @return
     */
    private List<Measure> findMetricasByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from metrica where idCubo = ?", new Object[]{
                    idCubo}, new RowMapper<Measure>() {

            public Measure mapRow(ResultSet rs, int i) throws SQLException {
                Measure filtro = new Measure();

                filtro.setColumn(rs.getString("coluna"));
                filtro.setDescription(rs.getString("descricao"));
                filtro.setFilterExpression(rs.getString("expressaoFiltro"));
                filtro.setCodigoFuncao(rs.getInt("funcao"));
                filtro.setId(rs.getInt("id"));
                filtro.setDefaultMeasure(rs.getInt("metricaPadrao") == 1);
                filtro.setName(rs.getString("nome"));

                return filtro;
            }
        });
    }

    /**
     * Apaga os filtros de um cubo.
     * @param cubo
     */
    @Transactional
    private void apagarFiltros(int idCubo) {
        getJdbcTemplate().update("delete from filtro where idcubo = ?", new Object[]{
                    idCubo});
    }

    /**
     * Apaga as métricas de cubo.
     * @param cubo
     */
    @Transactional
    private void apagarMetricas(int idCubo) {
        getJdbcTemplate().update("delete from metrica where idcubo = ?", new Object[]{
                    idCubo});
    }

    /**
     * Apaga os níveis de um cubo.
     * @param cubo
     */
    @Transactional
    private void apagarNiveis(int idCubo) {
        getJdbcTemplate().update("delete from cubo_nivel where idcubo = ?",
                new Object[]{idCubo});
    }

    class CuboShallowMapper implements RowMapper<Cube> {

        public Cube mapRow(ResultSet rs, int i) throws SQLException {
            Cube cubo = new Cube();
            cubo.setDescription(rs.getString("descricao"));
            cubo.setSchema(rs.getString("esquema"));
            cubo.setId(rs.getInt("id"));
            cubo.setName(rs.getString("nome"));
            cubo.setTable(rs.getString("tabela"));

            return cubo;
        }
    }

    class CuboDeepMapper extends CuboShallowMapper {

        @Override
        public Cube mapRow(ResultSet rs, int i) throws SQLException {
            Cube cubo = super.mapRow(rs, i);

            cubo.setLevels(findNiveisByCubo(cubo.getId()));
            cubo.setFilters(findFiltrosByCubo(cubo.getId()));
            cubo.setMeasures(findMetricasByCubo(cubo.getId()));

            return cubo;
        }
    }
}
