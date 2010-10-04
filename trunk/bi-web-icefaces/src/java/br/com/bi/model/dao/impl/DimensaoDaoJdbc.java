/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.DimensaoDao;
import br.com.bi.model.entity.metadata.Dimensao;
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
public class DimensaoDaoJdbc extends AbstractDaoJdbc implements DimensaoDao {

    public List<Dimensao> findAll() {
        return getJdbcTemplate().query("select * from dimensao", new DimensaoShallowMapper());
    }

    public Dimensao findById(int id) {
        return getJdbcTemplate().queryForObject("select * from dimensao where id = :id", new Object[]{
                    id}, new DimensaoDeepMapper());
    }

    public void salvar(Dimensao dimensao) {
        Map<String, Object> parameters = new HashMap<String, Object>();

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

    @Transactional
    public void apagar(int idDimensao) {
        apagarNiveis(findById(idDimensao));

        getJdbcTemplate().update("delete from dimensao where id = ?", new Object[]{
                    idDimensao});
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

    class DimensaoShallowMapper implements RowMapper<Dimensao> {

        public Dimensao mapRow(ResultSet rs, int i) throws SQLException {
            Dimensao dimensao = new Dimensao();

            dimensao.setDescricao(rs.getString("descricao"));
            dimensao.setId(rs.getInt("id"));
            dimensao.setNome(rs.getString("nome"));

            return dimensao;
        }
    }

    class DimensaoDeepMapper extends DimensaoShallowMapper {

        @Override
        public Dimensao mapRow(ResultSet rs, int i) throws SQLException {
            Dimensao dimensao = super.mapRow(rs, i);

            dimensao.setNiveis(findNiveisByDimensao(rs.getInt("id")));

            return dimensao;
        }
    }
}
