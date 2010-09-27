/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.cube.Cubo;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface CuboDao {

    public List<Cubo> getCubos();

    public Cubo getCubosById(int id);

    public void salvar(Cubo cubo);

    public void apagar(int id);
}
