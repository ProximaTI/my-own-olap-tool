/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.query.Consulta;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface ConsultaDao {

    public List<Consulta> getConsultas();

    public Consulta getConsultasById(int id);

    public void salvar(Consulta consulta);

    public void apagar(int id);
}
