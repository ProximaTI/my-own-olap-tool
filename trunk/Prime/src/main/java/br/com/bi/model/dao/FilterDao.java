/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.metadata.Filter;
import org.springframework.data.repository.Repository;

/**
 *
 * @author luiz
 */
public interface FilterDao extends Repository<Filter, Integer> {

    /**
     * Procura uma referência para um filtro dado seu nome.
     * @param name
     * @return 
     */
    public Filter findByName(String name);
}
