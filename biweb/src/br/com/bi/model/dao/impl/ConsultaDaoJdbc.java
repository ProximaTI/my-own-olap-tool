/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.ConsultaDao;
import br.com.bi.model.entity.query.Query;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class ConsultaDaoJdbc extends AbstractDaoJdbc implements ConsultaDao {

    public List<Query> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Query findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void salvar(Query consulta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void apagar(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
