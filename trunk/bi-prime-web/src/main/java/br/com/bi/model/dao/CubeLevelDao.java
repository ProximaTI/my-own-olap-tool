package br.com.bi.model.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Level;

public interface CubeLevelDao extends Repository<CubeLevel, Integer> {

	List<CubeLevel> findByLevel(Level level);

}
