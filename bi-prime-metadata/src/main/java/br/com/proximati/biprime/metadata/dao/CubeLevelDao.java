package br.com.proximati.biprime.metadata.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.proximati.biprime.metadata.entity.CubeLevel;
import br.com.proximati.biprime.metadata.entity.Level;

public interface CubeLevelDao extends Repository<CubeLevel, Integer> {

	List<CubeLevel> findByLevel(Level level);

}
