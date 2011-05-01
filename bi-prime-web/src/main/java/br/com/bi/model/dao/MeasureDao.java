/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.dao;

import br.com.bi.model.entity.metadata.Measure;
import org.springframework.data.repository.Repository;

/**
 *
 * @author luiz
 */
public interface MeasureDao extends Repository<Measure, Integer> {

    public Measure findByName(String name);
}
