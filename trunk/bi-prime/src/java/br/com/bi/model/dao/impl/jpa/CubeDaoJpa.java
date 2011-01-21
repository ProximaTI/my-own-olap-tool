package br.com.bi.model.dao.impl.jpa;

import br.com.bi.model.dao.CubeDao;
import br.com.bi.model.entity.metadata.Cube;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Implementação de Dao de cubos baseado em Jpa.
 *
 * @author Luiz Augusto
 */
public class CubeDaoJpa extends JpaDaoSupport implements CubeDao {

    public CubeDaoJpa() {
        super();
    }

    /**
     * Retorna lista com todos os cubos persistidos.
     * @return
     */
    public List<Cube> findAll() {
        return getJpaTemplate().find("select c from Cube c order by c.name");
    }

    /**
     * Retorna referência para um cubo dado o seu identificador.
     * @param id
     * @return
     */
    public Cube findById(Integer id) {
        return getJpaTemplate().find(Cube.class, id);
    }

    /**
     * Salva um cubo no banco de dados.
     * @param cube
     */
    public void save(Cube cube) {
        if (cube.getId() != null) {
            getJpaTemplate().merge(cube);
        } else {
            getJpaTemplate().persist(cube);
        }
    }

    /**
     * Apaga um cubo do banco de dados, dado seu identificador.
     * @param id
     */
    public void delete(Integer id) {
        Cube cube = findById(id);
        getJpaTemplate().refresh(cube);
        getJpaTemplate().remove(cube);
    }
}
