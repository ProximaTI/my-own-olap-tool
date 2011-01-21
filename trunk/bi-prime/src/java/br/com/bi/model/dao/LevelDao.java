package br.com.bi.model.dao;


import br.com.bi.model.entity.metadata.Level;

import java.util.List;

public interface LevelDao {
    /**
     * Retorna lista com todos os níveis persistidos.
     * @return
     */
    public List<Level> findAll();

    /**
     * Retorna referência para um nível dado o seu identificador.
     * @param id
     * @return
     */
    public Level findById(Integer id);

    /**
     * Salva um nível no banco de dados.
     * @param level
     */
    public void save(Level level);

    /**
     * Apaga um nível do banco de dados, dado seu identificador.
     * @param id
     */
    public void delete(Integer id);
}
