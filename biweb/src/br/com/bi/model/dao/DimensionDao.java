/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;


import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Level;

import java.util.List;

/**
 *
 * @author Luiz
 */
public interface DimensionDao {

    public List<Dimension> findAll();

    public Dimension findById(int id);

    public void salvar(Dimension dimensao);

    public void delete(int id);

    /**
     * Retorna os níveis que estão hierarquicamente abaixo do nível denotado por idnivel.
     * @param idnivel
     * @return
     */
    public List<Level> lowerLevels(int idnivel);

    /**
     * Retorna a dimensão à qual pertence o nível denotado por idnivel.
     * @param idnivel
     * @return
     */
    public Dimension findByLevelId(int idnivel);

    public Level findLevelById(int i);

    public List<Level> findAllLevels();
}
