/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao.impl;

import br.com.bi.model.dao.ConsultaDao;
import br.com.bi.model.entity.query.Consulta;
import java.util.List;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Luiz
 */
public class ConsultaDaoJdbc extends JdbcDaoSupport implements ConsultaDao {

    public List<Consulta> getConsultas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Consulta getConsultasById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void salvar(Consulta consulta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void apagar(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
