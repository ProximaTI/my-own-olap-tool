package br.com.bi.model.driver;

import java.util.List;

public interface RdbmsDriver {

    /**
     * Retorna os esquemas do banco de dados.
     * @return
     */
    public List<String> getSchemas();

    /**
     * Retorna as tabelas e views de um determinado esquema.
     * @param schema
     * @return
     */
    public List<String> getTables(String schema);
}
