/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.metadata.Dimensao;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface DimensaoDao {

    public List<Dimensao> findAll();

    public Dimensao findById(int id);

    public void salvar(Dimensao dimensao);

    public void apagar(int id);
}
