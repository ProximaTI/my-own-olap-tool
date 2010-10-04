/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.metadata.Dimension;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface DimensaoDao {

    public List<Dimension> findAll();

    public Dimension findById(int id);

    public void salvar(Dimension dimensao);

    public void apagar(int id);
}
