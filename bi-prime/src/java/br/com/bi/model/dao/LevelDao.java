package br.com.bi.model.dao;

import br.com.bi.model.entity.metadata.Level;
import org.springframework.data.repository.Repository;

/**
 * Data access object para entidade nível.
 * @author luiz
 */
public interface LevelDao extends Repository<Level, Integer> {

    /**
     * Procura uma referência para um nível dado seu nome.
     * @param name
     * @return 
     */
    public Level findByName(String name);
}
