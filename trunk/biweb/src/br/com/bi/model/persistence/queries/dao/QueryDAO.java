package br.com.bi.model.persistence.queries.dao;


import br.com.bi.model.entity.queries.Query;

import java.util.List;

public interface QueryDAO {
    public static final String BEAN_NAME = "queryDAO";

    public List<Query> findAll();

    public Query findById(Integer id);

    public void insert(Query query);

    public void delete(Query query);

    public void update(Query query);
}
