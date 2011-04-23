package br.com.bi.model.dao;

import org.springframework.data.repository.Repository;

import br.com.bi.model.entity.metadata.Dimension;

public interface DimensionDao extends Repository<Dimension, Integer> {
}
