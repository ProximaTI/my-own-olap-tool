/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.CuboDao;
import br.com.bi.model.entity.cube.Cubo;
import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Luiz
 */
public class CuboDaoJdbc extends JdbcDaoSupport implements CuboDao {

    public List<Cubo> getCubos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Cubo getCubosById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void salvar(Cubo cubo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void apagar(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
