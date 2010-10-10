package br.com.bi.model.persistence.queries.dao.impl.jdbc;


import br.com.bi.model.entity.queries.Query;
import br.com.bi.model.persistence.queries.dao.NodeDAO;
import br.com.bi.model.persistence.queries.dao.QueryDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementadação baseada em Jdbc para DAO de consultas.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class JdbcQueryDAO extends JdbcDaoSupport implements QueryDAO {
    /**
     * Insere uma nova consulta no banco de dados.
     * @param query
     */
    @Transactional(propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void insert(Query query) {
        nodeDAO.insert(query.getColumnsAxis());
        nodeDAO.insert(query.getRowsAxis());

        Integer id = queryIncrementer.nextIntValue();

        getJdbcTemplate().update("insert into query (id, name, cubeName, " +
                                 "columnsAxisNonEmpty, rowsAxisNonEmpty, " +
                                 "columnsAxis, rowsAxis) " +
                                 "values (?, ?, ?, ?, ?, ?, ?, ?) ",
                                 new Object[] { id, query.getName(),
                                                query.getCubeName(),
                                                query.isColumnsAxisNonEmpty(),
                                                query.isRowsAxisNonEmpty(),
                                                query.getColumnsAxis().getId(),
                                                query.getRowsAxis().getId() });
        query.setId(id);
    }

    /**
     * Apaga uma consulta do banco de dados. Deve ser fornecida uma consulta
     * completamente carregada, pois os seus eixos também serão apagados.
     * @param query
     */
    @Transactional(propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void delete(Query query) {
        getJdbcTemplate().update("delete from query where id = ?",
                                 new Object[] { query.getId() });
        nodeDAO.delete(query.getColumnsAxis());
        nodeDAO.delete(query.getRowsAxis());
    }

    /**
     * Atualiza uma consulta no banco de dados.
     * @param query
     */
    @Transactional(propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void update(Query query) {
        nodeDAO.update(query.getColumnsAxis());
        nodeDAO.update(query.getRowsAxis());

        getJdbcTemplate().update("update query set name = ?, cubeName = ?, " +
                                 "columnsAxisNonEmpty = ?, rowsAxisNonEmpty = ?, " +
                                 "columnsAxis = ?, rowsAxis = ? where id = ?",
                                 new Object[] { query.getName(),
                                                query.getCubeName(),
                                                query.isColumnsAxisNonEmpty(),
                                                query.isRowsAxisNonEmpty(),
                                                query.getColumnsAxis().getId(),
                                                query.getRowsAxis().getId(),
                                                query.getId() });
    }

    /**
     * Retorna todas as consultas do banco de dados.
     * @return
     */
    public List<Query> findAll() {
        return getJdbcTemplate().query("select * from query order by name",
                                       new QueryMapper());
    }

    /**
     * Retorna um consulta dado o seu identificador.
     * @param id
     * @return
     */
    public Query findById(Integer id) {
        return (Query)getJdbcTemplate().queryForObject("select * from query " +
                                                       "where id = ?",
                                                       new Object[] { id },
                                                       new QueryMapper());
    }

    class QueryMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Query query = new Query();
            query.setId(rs.getInt("id"));
            query.setName(rs.getString("name"));
            query.setCubeName(rs.getString("cubeName"));
            query.setColumnsAxisNonEmpty(rs.getBoolean("columnsAxisNonEmpty"));
            query.setRowsAxisNonEmpty(rs.getBoolean("rowsAxisNonEmpty"));
            query.setColumnsAxis(nodeDAO.findById(rs.getInt("columnsAxis")));
            query.setRowsAxis(nodeDAO.findById(rs.getInt("rowsAxis")));

            query.setPersisted(true);

            return query;
        }
    }

    private NodeDAO nodeDAO;

    public NodeDAO getNodeDAO() {
        return nodeDAO;
    }

    public void setNodeDAO(NodeDAO nodeDAO) {
        this.nodeDAO = nodeDAO;
    }

    private DataFieldMaxValueIncrementer queryIncrementer;

    public DataFieldMaxValueIncrementer getQueryIncrementer() {
        return queryIncrementer;
    }

    public void setQueryIncrementer(DataFieldMaxValueIncrementer queryIncrementer) {
        this.queryIncrementer = queryIncrementer;
    }
}
