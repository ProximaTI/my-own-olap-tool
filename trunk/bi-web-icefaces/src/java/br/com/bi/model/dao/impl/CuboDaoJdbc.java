/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.CuboDao;
import br.com.bi.model.entity.metadata.Cubo;
import br.com.bi.model.entity.metadata.CuboNivel;
import br.com.bi.model.entity.metadata.Filtro;
import br.com.bi.model.entity.metadata.Metrica;
import br.com.bi.model.entity.metadata.Nivel;
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
    public List<Cubo> findAll() {
        return getJdbcTemplate().query("select * from cubo order by nome", new CuboShallowMapper());
    }

    /**
     * Retorna um cubo dado o seu identificador (carga profunda).
     * @param id
     * @return
     */
    public Cubo findById(int id) {
        return getJdbcTemplate().queryForObject("select * from cubo where id = ?", new Object[]{
                    id}, new CuboDeepMapper());
    }

    /**
     * Salva um cubo no banco de dados.
     * @param cubo
     */
    @Transactional
    public void salvar(Cubo cubo) {
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("nome", cubo.getNome());
        parameters.put("descricao", cubo.getDescricao());
        parameters.put("esquema", cubo.getEsquema());
        parameters.put("tabela", cubo.getTabela());

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
    private void salvarMetricas(Cubo cubo) {
        List metricas = new ArrayList();

        for (Metrica metrica : cubo.getMetricas()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("nome", metrica.getNome());
            parameters.put("descricao", metrica.getDescricao());
            parameters.put("funcao", metrica.getCodigoFuncao());
            parameters.put("coluna", metrica.getColuna());
            parameters.put("expressaoFiltro", metrica.getExpressaoFiltro());
            parameters.put("metricaPadrao", metrica.isMetricaPadrao() ? 1 : 0);

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
    private void salvarFiltros(Cubo cubo) {
        List filtros = new ArrayList();

        for (Filtro filtro : cubo.getFiltros()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("nome", filtro.getNome());
            parameters.put("descricao", filtro.getDescricao());
            parameters.put("expressao", filtro.getExpressao());

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
    private void salvarNiveis(Cubo cubo) {
        List niveis = new ArrayList();

        for (CuboNivel nivel : cubo.getNiveis()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("idnivel", nivel.getNivel().getId());
            parameters.put("colunaJuncao", nivel.getColunaJuncao());

            if (!nivel.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("nivel");
                insert.execute(parameters);
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update cubo_nivel set colunaJuncao = :colunaJuncao"
                        + " where idcubo = :id and idnivel = :idnivel");
                update.updateByNamedParam(parameters);
            }

            niveis.add(nivel.getNivel().getId());
        }

        if (niveis.size() > 0) {
            getJdbcTemplate().update("delete from cubo_nivel "
                    + "where not id in (:ids) and idcubo = :idcubo".replace(":ids", listToString(niveis)).
                    replace(":idcubo", Integer.toString(cubo.getId())));
        }
    }

    /**
     * Apaga do banco de dados um cubo dado o seu identificador.
     * @param id
     */
    @Transactional
    public void apagar(int id) {
        // realiza carga completa do cubo
        Cubo cubo = findById(id);

        apagarFiltros(cubo.getId());
        apagarMetricas(cubo.getId());
        apagarNiveis(cubo.getId());

        getJdbcTemplate().update("delete from cubo where id = ?", new Object[]{
                    id});
    }

    private List<CuboNivel> findNiveisByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from cubo_nivel where idcubo = ?", new Object[]{
                    idCubo}, new RowMapper<CuboNivel>() {

            public CuboNivel mapRow(ResultSet rs, int i) throws SQLException {
                CuboNivel nivel = new CuboNivel();
                nivel.setNivel(findNivelById(rs.getInt("idnivel")));
                nivel.setColunaJuncao(rs.getString("colunaJuncao"));
                return nivel;
            }

            private Nivel findNivelById(int aInt) {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        });
    }

    /**
     * Retorna os filtros de um cubo dado seu identificador.
     * @param idCubo
     * @return
     */
    private List<Filtro> findFiltrosByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from filtro where idcubo = ?", new Object[]{
                    idCubo}, new RowMapper<Filtro>() {

            public Filtro mapRow(ResultSet rs, int i) throws SQLException {
                Filtro filtro = new Filtro();

                filtro.setDescricao(rs.getString("descricao"));
                filtro.setExpressao(rs.getString("expressao"));
                filtro.setId(rs.getInt("id"));
                filtro.setNome(rs.getString("nome"));

                return filtro;
            }
        });
    }

    /**
     * Retorna as métricas de cubo dado o seu identificador.
     * @param idCubo
     * @return
     */
    private List<Metrica> findMetricasByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from metrica where idCubo = ?", new Object[]{
                    idCubo}, new RowMapper<Metrica>() {

            public Metrica mapRow(ResultSet rs, int i) throws SQLException {
                Metrica filtro = new Metrica();

                filtro.setColuna(rs.getString("coluna"));
                filtro.setDescricao(rs.getString("descricao"));
                filtro.setExpressaoFiltro(rs.getString("expressaoFiltro"));
                filtro.setCodigoFuncao(rs.getInt("funcao"));
                filtro.setId(rs.getInt("id"));
                filtro.setMetricaPadrao(rs.getInt("metricaPadrao") == 1);
                filtro.setNome(rs.getString("nome"));

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

    class CuboShallowMapper implements RowMapper<Cubo> {

        public Cubo mapRow(ResultSet rs, int i) throws SQLException {
            Cubo cubo = new Cubo();
            cubo.setDescricao(rs.getString("descricao"));
            cubo.setEsquema(rs.getString("esquema"));
            cubo.setId(rs.getInt("id"));
            cubo.setNome(rs.getString("nome"));
            cubo.setTabela(rs.getString("tabela"));

            return cubo;
        }
    }

    class CuboDeepMapper extends CuboShallowMapper {

        @Override
        public Cubo mapRow(ResultSet rs, int i) throws SQLException {
            Cubo cubo = super.mapRow(rs, i);

            cubo.setNiveis(findNiveisByCubo(cubo.getId()));
            cubo.setFiltros(findFiltrosByCubo(cubo.getId()));
            cubo.setMetricas(findMetricasByCubo(cubo.getId()));

            return cubo;
        }
    }
}
