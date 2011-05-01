package br.com.proximati.biprime.metadata.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Property;

public interface PropertyDao extends Repository<Property, Integer> {

	List<Property> findByLevel(Level level);
}
