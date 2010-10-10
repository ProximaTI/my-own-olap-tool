/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.metadata.Cube;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface CuboDao {

    public List<Cube> findAll();

    public Cube findById(int id);

    public void save(Cube cubo);

    public void delete(int id);
}
