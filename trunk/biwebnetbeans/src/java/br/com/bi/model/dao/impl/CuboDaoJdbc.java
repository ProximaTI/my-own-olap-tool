/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.CuboDao;
import br.com.bi.model.entity.metadata.Cubo;
import br.com.bi.model.entity.metadata.Dimensao;
import br.com.bi.model.entity.metadata.Filtro;
import br.com.bi.model.entity.metadata.Metrica;
import br.com.bi.model.entity.metadata.Nivel;
import br.com.bi.model.entity.metadata.Propriedade;
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
    public List<Cubo> findAllCubos() {
        return getJdbcTemplate().query("select * from cubo order by nome", new CuboShallowMapper());
    }

    /**
     * Retorna um cubo dado o seu identificador (carga profunda).
     * @param id
     * @return
     */
    public Cubo findCuboById(int id) {
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
        salvarDimensoes(cubo);
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
     * Persiste as dimensões de um cubo.
     * @param cubo
     */
    @Transactional
    private void salvarDimensoes(Cubo cubo) {
        List dimensoes = new ArrayList();

        for (Dimensao dimensao : cubo.getDimensoes()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idcubo", cubo.getId());
            parameters.put("nome", dimensao.getNome());
            parameters.put("descricao", dimensao.getDescricao());

            if (!dimensao.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("dimensao").usingGeneratedKeyColumns("id");
                dimensao.setId(insert.executeAndReturnKey(parameters).intValue());
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update dimensao set nome = :nome, "
                        + "descricao = :descricao where id = :id");
                parameters.put("id", dimensao.getId());
                update.updateByNamedParam(parameters);
            }

            salvarNiveis(dimensao);

            dimensoes.add(dimensao.getId());
        }

        if (dimensoes.size() > 0) {
            getJdbcTemplate().update("delete from dimensao where not id in (:ids) and idcubo = :idcubo".
                    replace(":ids", listToString(dimensoes)).replace(":idcubo", Integer.
                    toString(cubo.getId())));
        }
    }

    /**
     * Persiste os níveis de uma dimensão.
     * @param dimensao
     */
    @Transactional
    private void salvarNiveis(Dimensao dimensao) {
        List niveis = new ArrayList();

        for (Nivel nivel : dimensao.getNiveis()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("iddimensao", dimensao.getId());
            parameters.put("nome", nivel.getNome());
            parameters.put("descricao", nivel.getDescricao());
            parameters.put("esquema", nivel.getEsquema());
            parameters.put("tabela", nivel.getTabela());
            parameters.put("juncaoNivelSuperior", nivel.getJuncaoNivelSuperior());

            if (!nivel.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("nivel").usingGeneratedKeyColumns("id");
                nivel.setId(insert.executeAndReturnKey(parameters).intValue());
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update nivel set nome = :nome, "
                        + "descricao = :descricao, esquema = :esquema, tabela = :tabela, "
                        + "juncaoNivelSuperior = :juncaoNivelSuperior where id = :id");
                parameters.put("id", nivel.getId());
                update.updateByNamedParam(parameters);
            }

            salvarPropriedades(nivel);

            niveis.add(nivel.getId());
        }

        if (niveis.size() > 0) {
            getJdbcTemplate().update("delete from nivel where not id in (:ids) and iddimensao = :iddimensao".
                    replace(":ids", listToString(niveis)).replace(":iddimensao", Integer.
                    toString(dimensao.getId())));
        }
    }

    /**
     * Persiste as propriedades de um nível.
     * @param nivel
     */
    @Transactional
    private void salvarPropriedades(Nivel nivel) {
        List propriedades = new ArrayList();

        for (Propriedade propriedade : nivel.getPropriedades()) {

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("idnivel", nivel.getId());
            parameters.put("nome", propriedade.getNome());
            parameters.put("descricao", propriedade.getDescricao());
            parameters.put("coluna", propriedade.getColuna());
            parameters.put("propriedadeCodigo", propriedade.isPropriedadeCodigo() ? 1 : 0);
            parameters.put("propriedadeNome", propriedade.isPropriedadeNome() ? 1 : 0);

            if (!propriedade.isPersisted()) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(getDataSource());
                insert.withTableName("propriedade").usingGeneratedKeyColumns("id");
                propriedade.setId(insert.executeAndReturnKey(parameters).
                        intValue());
            } else {
                SqlUpdate update =
                        new SqlUpdate(getDataSource(), "update propriedade set nome = :nome, "
                        + "descricao = :descricao, coluna = :coluna, propriedadeCodigo = :propriedadeCodigo, "
                        + "propriedadeNome = :propriedadeNome where id = :id");
                parameters.put("id", propriedade.getId());
                update.updateByNamedParam(parameters);
            }

            propriedades.add(propriedade.getId());
        }

        if (propriedades.size() > 0) {
            getJdbcTemplate().update("delete from propriedade where not id in (:ids) and idnivel = :idnivel".
                    replace(":ids", listToString(propriedades)).replace(":idnivel", Integer.
                    toString(nivel.getId())));
        }
    }

    private String listToString(List<Integer> l) {
        StringBuilder scrap = new StringBuilder();

        for (Integer i : l) {
            scrap.append(i.toString()).append(",");
        }

        scrap.delete(scrap.length() - 1, scrap.length());

        return scrap.toString();
    }

    /**
     * Apaga do banco de dados um cubo dado o seu identificador.
     * @param id
     */
    @Transactional
    public void apagar(int id) {
        // realiza carga completa do cubo
        Cubo cubo = findCuboById(id);

        apagarFiltros(cubo);
        apagarMetricas(cubo);
        apagarDimensoes(cubo);

        getJdbcTemplate().update("delete from cubo where id = ?", new Object[]{
                    id});
    }

    /**
     * Retorna a lista de dimensões dado o identificador do cubo.
     * @param idCubo
     * @return
     */
    private List<Dimensao> findDimensoesByCubo(int idCubo) {
        return getJdbcTemplate().query("select * from dimensao where idcubo = ?", new Object[]{
                    idCubo}, new RowMapper<Dimensao>() {

            public Dimensao mapRow(ResultSet rs, int i) throws SQLException {
                Dimensao dimensao = new Dimensao();
                dimensao.setDescricao(rs.getString("descricao"));
                dimensao.setId(rs.getInt("id"));
                dimensao.setNiveis(findNiveisByDimensao(dimensao.getId()));
                dimensao.setNome(rs.getString("nome"));
                return dimensao;
            }
        });
    }

    /**
     * Retorna a lista de níveis dado o identificador da dimensão.
     * @param idDimensao
     * @return
     */
    private List<Nivel> findNiveisByDimensao(int idDimensao) {
        return getJdbcTemplate().query("select * from nivel where iddimensao = ?", new Object[]{
                    idDimensao}, new RowMapper<Nivel>() {

            public Nivel mapRow(ResultSet rs, int i) throws SQLException {
                Nivel nivel = new Nivel();
                nivel.setDescricao(rs.getString("descricao"));
                nivel.setEsquema(rs.getString("esquema"));
                nivel.setId(rs.getInt("id"));
                nivel.setJuncaoNivelSuperior(rs.getString("juncaoNivelSuperior"));
                nivel.setNome(rs.getString("nome"));
                nivel.setPropriedades(findPropriedadesByNivel(nivel.getId()));
                nivel.setTabela(rs.getString("tabela"));
                return nivel;
            }
        });
    }

    /**
     * Retorna as propriedades de nível dado seu identificador.
     * @param idNivel
     * @return
     */
    private List<Propriedade> findPropriedadesByNivel(int idNivel) {
        return getJdbcTemplate().query("select * from propriedade where idnivel = ?", new Object[]{
                    idNivel}, new RowMapper<Propriedade>() {

            public Propriedade mapRow(ResultSet rs, int i) throws SQLException {
                Propriedade propriedade = new Propriedade();

                propriedade.setColuna(rs.getString("coluna"));
                propriedade.setDescricao(rs.getString("descricao"));
                propriedade.setId(rs.getInt("id"));
                propriedade.setNome(rs.getString("nome"));
                propriedade.setPropriedadeCodigo(rs.getInt("propriedadeCodigo")
                        == 1);
                propriedade.setPropriedadeNome(rs.getInt("propriedadeNome") == 1);
                return propriedade;
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
    private void apagarFiltros(Cubo cubo) {
        getJdbcTemplate().update("delete from filtro where idcubo = ?", new Object[]{
                    cubo.getId()});
    }

    /**
     * Apaga as métricas de cubo.
     * @param cubo
     */
    @Transactional
    private void apagarMetricas(Cubo cubo) {
        getJdbcTemplate().update("delete from metrica where idcubo = ?", new Object[]{
                    cubo.getId()});
    }

    /**
     * Apaga as dimensões de um cubo.
     * @param cubo
     */
    @Transactional
    private void apagarDimensoes(Cubo cubo) {
        for (Dimensao dimensao : cubo.getDimensoes()) {
            apagarNiveis(dimensao);
        }

        getJdbcTemplate().update("delete from dimensao where idcubo = ?", new Object[]{cubo.
                    getId()});
    }

    /**
     * Apaga os níveis de uma dimensão.
     * @param dimensao
     */
    @Transactional
    private void apagarNiveis(Dimensao dimensao) {
        for (Nivel nivel : dimensao.getNiveis()) {
            apagarPropriedades(nivel);
        }

        getJdbcTemplate().update("delete from nivel where iddimensao = ?", new Object[]{dimensao.
                    getId()});
    }

    /**
     * Apagar as propriedades de nível.
     * @param nivel
     */
    @Transactional
    private void apagarPropriedades(Nivel nivel) {
        getJdbcTemplate().update("delete from propriedade where idnivel = ?", new Object[]{nivel.
                    getId()});
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

            cubo.setDimensoes(findDimensoesByCubo(cubo.getId()));
            cubo.setFiltros(findFiltrosByCubo(cubo.getId()));
            cubo.setMetricas(findMetricasByCubo(cubo.getId()));

            return cubo;
        }
    }
}
