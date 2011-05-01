/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.metadata.dao;

import br.com.proximati.biprime.metadata.entity.Cube;

import org.springframework.data.repository.Repository;

/**
 *
 * @author Luiz
 */
public interface CubeDao extends Repository<Cube, Integer> {

    public Cube findByName(String name);
}
