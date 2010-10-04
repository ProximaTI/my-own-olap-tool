/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.query.Query;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface ConsultaDao {

    public List<Query> findAll();

    public Query findById(int id);

    public void salvar(Query consulta);

    public void apagar(int id);
}
