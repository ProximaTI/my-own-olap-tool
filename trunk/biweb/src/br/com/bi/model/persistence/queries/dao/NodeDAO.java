package br.com.bi.model.persistence.queries.dao;

import br.com.bi.model.entity.queries.Node;

public interface NodeDAO {
    public Node findById(Integer id);

    public void insert(Node node);

    public void delete(Node node);

    public void update(Node node);
}
