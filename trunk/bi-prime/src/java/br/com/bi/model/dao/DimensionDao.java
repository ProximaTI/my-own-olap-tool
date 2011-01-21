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
public interface DimensionDao {
    /**
     * Retorna lista com todos as dimensões persistidas.
     * @return
     */
    public List<Dimension> findAll();

    /**
     * Retorna referência para uma dimensão dado o seu identificador.
     * @param id
     * @return
     */
    public Dimension findById(Integer id);

    /**
     * Salva uma dimensão no banco de dados.
     * @param dimension
     */
    public void save(Dimension dimension);

    /**
     * Apaga uma dimensão do banco de dados, dado seu identificador.
     * @param id
     */
    public void delete(Integer id);
}
