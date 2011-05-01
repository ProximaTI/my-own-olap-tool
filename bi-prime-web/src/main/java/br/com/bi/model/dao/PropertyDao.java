package br.com.bi.model.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Property;

public interface PropertyDao extends Repository<Property, Integer> {

	List<Property> findByLevel(Level level);
}
