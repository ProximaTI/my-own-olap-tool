package br.com.proximati.biprime.metadata.dao;

import java.util.List;

import br.com.proximati.biprime.metadata.entity.Dimension;
import br.com.proximati.biprime.metadata.entity.Level;
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

	public List<Level> findByDimension(Dimension dimension);
}
