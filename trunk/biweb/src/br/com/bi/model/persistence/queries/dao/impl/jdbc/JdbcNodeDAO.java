package br.com.bi.model.persistence.queries.dao.impl.jdbc;


import br.com.bi.model.entity.queries.Node;
import br.com.bi.model.persistence.queries.dao.NodeDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementa��o do DAO de n�s atrav�s de JDBC.
 * @author Luiz Augusto Garcia da Silva
 */
public class JdbcNodeDAO extends JdbcDaoSupport implements NodeDAO {
    /**
     * Insere um novo n� no banco de dados.
     * @param node
     */
    @Transactional(propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void insert(Node node) {
        Integer id = nodeIncrementer.nextIntValue();

        getJdbcTemplate().update("insert into node (id, associateElement, " +
                                 "indice, name, type, parentId) " +
                                 "values (?, ?, ?, ?, ?, ?)",
                                 new Object[] { id, node.getAssociateElement(),
                                                node.getIndice(),
                                                node.getName(), node.getType(),
                                                node.getParentNode() != null ?
                                                node.getParentNode().getId() :
                                                null });
        node.setId(id);

        for (Node child : node.getChildren()) {
            insert(child);
        }
    }

    /**
     * Apaga um n� e seus filhos do banco de dados.
     * Deve ser fornecido como par�metro um n� completamente carregado, porque
     * primeiro ser�o apagados seus filhos e s� depois o pr�prio ser� apagado.
     * @param node
     */
    @Transactional(propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void delete(Node node) {
        for (Node child : node.getChildren())
            delete(child);

        getJdbcTemplate().update("delete from node where parentId = ?",
                                 new Object[] { node.getId() });

        getJdbcTemplate().update("delete from node where id = ?",
                                 new Object[] { node.getId() });
    }

    /**
     * Atualiza um n� no banco de dados.
     * @param node
     */
    @Transactional(propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void update(Node node) {
        getJdbcTemplate().update("update node set associateElement = ?, " +
                                 "indice = ?, name = ?, type = ?, parentId = ?" +
                                 " where id = ?",
                                 new Object[] { node.getAssociateElement(),
                                                node.getIndice(),
                                                node.getName(), node.getType(),
                                                node.getParentNode() != null ?
                                                node.getParentNode().getId() :
                                                null, node.getId() });

        for (Node child : node.getChildren())
            delete(child);

        for (Node child : node.getChildren())
            insert(child);
    }

    /**
     * Retorna um n� completamente carregado dado o seu identificador.
     * @param id
     * @return
     */
    public Node findById(Integer id) {
        /* Para atribui��o das refer�ncias reais entros os objetos pai e seus filhos.*/
        Map<Integer, Node> nodeCache = new HashMap<Integer, Node>();

        return (Node)getJdbcTemplate().queryForObject("select * from node " +
                                                      "where id = ?",
                                                      new Object[] { id },
                                                      new NodeMapper(nodeCache));
    }

    /**
     * Retorna os n�s filhos de um n�, dado o identificador deste n� pai.
     * @param parentId
     * @return
     */
    private List<Node> findChildren(Integer parentId,
                                    Map<Integer, Node> cache) {
        return getJdbcTemplate().query("select * from node where parentId = ? " +
                                       "order by indice",
                                       new Object[] { parentId },
                                       new NodeMapper(cache));
    }

    private DataFieldMaxValueIncrementer nodeIncrementer;

    public DataFieldMaxValueIncrementer getNodeIncrementer() {
        return nodeIncrementer;
    }

    public void setNodeIncrementer(DataFieldMaxValueIncrementer nodeIncrementer) {
        this.nodeIncrementer = nodeIncrementer;
    }

    /**
     * Classe respons�vel por mapear um resultset em um n�.
     * @author Luiz Augusto Garcia da Silva
     */
    class NodeMapper implements RowMapper {
        private Map<Integer, Node> nodeCache;

        public NodeMapper(Map<Integer, Node> nodeCache) {
            this.nodeCache = nodeCache;
        }

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Node node = new Node(rs.getInt("id"), rs.getString("name"));

            nodeCache.put(node.getId(), node);

            node.setAssociateElement(rs.getString("associateElement"));
            node.setChildren(findChildren(node.getId(), nodeCache));
            node.setIndice(rs.getInt("indice"));
            node.setType(rs.getInt("type"));
            node.setParentNode(nodeCache.get(rs.getInt("parentId")));
            node.setPersisted(true);

            return node;
        }
    }

}
