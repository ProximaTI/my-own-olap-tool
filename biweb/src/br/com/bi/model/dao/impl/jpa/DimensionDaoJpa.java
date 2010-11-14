package br.com.bi.model.dao.impl.jpa;


import br.com.bi.model.dao.DimensionDao;
import br.com.bi.model.entity.metadata.Dimension;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;


/**
 * Implementação de Dao de dimensões baseado em Jpa.
 *
 * @author Luiz Augusto
 */
public class DimensionDaoJpa extends JpaDaoSupport implements DimensionDao {
    public DimensionDaoJpa() {
        super();
    }

    /**
     * Retorna lista com todas dimensões persistidas.
     * @return
     */
    public List<Dimension> findAll() {
        return getJpaTemplate().find("select d from Dimension d order by d.name");
    }

    /**
     * Retorna referência para uma dimensão dado seu identificador.
     * @param id
     * @return
     */
    public Dimension findById(Integer id) {
        return getJpaTemplate().find(Dimension.class, id);
    }

    /**
     * Salva uma dimensão no banco de dados.
     * @param dimension
     */
    public void save(Dimension dimension) {
        if (dimension.getId() != null)
            getJpaTemplate().merge(dimension);
        else
            getJpaTemplate().persist(dimension);
    }

    /**
     * Remove uma dimensão do banco de dados.
     * @param id
     */
    public void delete(Integer id) {
        getJpaTemplate().remove(findById(id));
    }
}
