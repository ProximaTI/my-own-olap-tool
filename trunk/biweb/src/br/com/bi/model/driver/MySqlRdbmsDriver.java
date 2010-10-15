package br.com.bi.model.driver;

import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;


public class MySqlRdbmsDriver extends JdbcDaoSupport implements RdbmsDriver {
    public MySqlRdbmsDriver() {
        super();
    }

    /**
     * Retorna os esquemas do banco de dados.
     * @return
     */
    public List<String> getSchemas() {
        return getJdbcTemplate().queryForList("SELECT SCHEMA_NAME\n" +
                "  FROM INFORMATION_SCHEMA.SCHEMATA", String.class);
    }

    /**
     * Retorna as tabelas e views de um determinado esquema.
     * @param schema
     * @return
     */
    public List<String> getTables(String schema) {
        if (schema != null)
            return getJdbcTemplate().queryForList("SELECT TABLE_NAME\n" +
                    "  FROM INFORMATION_SCHEMA.TABLES\n" +
                    " WHERE TABLE_SCHEMA = ?\n" +
                    "UNION ALL\n" +
                    "SELECT TABLE_NAME\n" +
                    "  FROM INFORMATION_SCHEMA.VIEWS\n" +
                    " WHERE TABLE_SCHEMA = ?", new Object[] { schema, schema },
                    String.class);
        else
            return Collections.emptyList();
    }
}