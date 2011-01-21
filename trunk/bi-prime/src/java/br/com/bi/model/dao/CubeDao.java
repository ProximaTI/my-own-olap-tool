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
public interface CubeDao {
    /**
     * Retorna lista com todos os cubos persistidos.
     * @return
     */
    public List<Cube> findAll();

    /**
     * Retorna referência para um cubo dado o seu identificador.
     * @param id
     * @return
     */
    public Cube findById(Integer id);

    /**
     * Salva um cubo no banco de dados.
     * @param cube
     */
    public void save(Cube cube);

    /**
     * Apaga um cubo do banco de dados, dado seu identificador.
     * @param id
     */
    public void delete(Integer id);
}
