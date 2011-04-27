/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.server;

import br.com.bi.model.Application;
import br.com.bi.olapql.language.query.translator.QuerySqlTranslator;
import java.sql.ResultSet;
import javax.sql.DataSource;

/**
 *
 * @author luiz
 */
public class OlapQlServer {

    public ResultSet execute(String olapql) throws Exception {
        DataSource dataSource = (DataSource) Application.getBean("dataSource");

        QuerySqlTranslator translator = new QuerySqlTranslator();
        
        return dataSource.getConnection().createStatement().executeQuery(translator.translateOlapQlInstruction(olapql));
    }
}
