package br.com.bi.model.dao.impl.jpa;


import br.com.bi.model.dao.LevelDao;
import br.com.bi.model.entity.metadata.Level;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;


/**
 * Implementação de Dao de nível baseado em Jpa.
 *
 * @author Luiz Augusto
 */
public class LevelDaoJpa extends JpaDaoSupport implements LevelDao {
    public LevelDaoJpa() {
        super();
    }

    /**
     * Retorna lista com todos os níveis persistidos.
     * @return
     */
    public List<Level> findAll() {
        return getJpaTemplate().find("select l from Level order by l.name");
    }

    /**
     * Retorna referência para um nível dado o seu identificador.
     * @param id
     * @return
     */
    public Level findById(Integer id) {
        return getJpaTemplate().find(Level.class, id);
    }

    /**
     * Salva um nível no banco de dados.
     * @param level
     */
    public void save(Level level) {
        if (level.getId() != null)
            getJpaTemplate().merge(level);
        else
            getJpaTemplate().persist(level);
    }

    /**
     * Apaga um nível do banco de dados, dado seu identificador.
     * @param id
     */
    public void delete(Integer id) {
        getJpaTemplate().remove(findById(id));
    }
}
