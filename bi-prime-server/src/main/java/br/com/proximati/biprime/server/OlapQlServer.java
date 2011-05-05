/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server;

import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.server.olapql.language.query.Select;
import br.com.proximati.biprime.server.olapql.language.query.translator.QuerySqlTranslator;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableModelBuilder;
import java.sql.ResultSet;
import javax.sql.DataSource;

/**
 *
 * @author luiz
 */
public class OlapQlServer {

    public ResultSet openResultset(String olapql) throws Exception {
        DataSource dataSource = (DataSource) Application.getBean("dataSource");

        QuerySqlTranslator translator = new QuerySqlTranslator();

        ResultSet resultset = dataSource.getConnection().createStatement().executeQuery(translator.translateOlapQlInstruction(olapql));

        long a = System.currentTimeMillis();

        PivotTableModelBuilder builder = new PivotTableModelBuilder();
        builder.build((Select) translator.getInstruction().jjtGetChild(0), resultset, translator.getAxisNodeCoordinateMap(), translator.getAxisNodeMetadataMap());
        long b = System.currentTimeMillis();

        System.out.println("tempo gasto na construção do pivot table model: " + (b - a));

        resultset = dataSource.getConnection().createStatement().executeQuery(translator.translateOlapQlInstruction(olapql));
        
        return resultset;
    }
}
