/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server;

import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.query.translator.QuerySqlTranslator;
import br.com.proximati.biprime.server.olapql.language.query.translator.TranslationContext;
import br.com.proximati.biprime.server.olapql.query.result.QueryResultModel;
import br.com.proximati.biprime.server.olapql.query.result.QueryResultBuilder;
import br.com.proximati.biprime.view.itext.QueryResultRenderer;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luiz
 */
public class OlapQlServer {

    public QueryResultModel execute(String query) throws Exception {
        DataSource dataSource = (DataSource) Application.getBean("dataSource");

        QueryParser parser = new QueryParser(IOUtils.toInputStream(query));
        QuerySqlTranslator translator = new QuerySqlTranslator();

        TranslationContext context = translator.translate((ASTSelect) parser.select());
        ResultSet resultset = dataSource.getConnection().createStatement().executeQuery(context.getOutput().toString());
        System.out.println(context.getOutput());

        long a = System.currentTimeMillis();

        QueryResultBuilder builder = new QueryResultBuilder();
        QueryResultModel model = builder.build(resultset, context);
        long b = System.currentTimeMillis();

        System.out.println("tempo gasto na construção do pivot table model: " + (b - a));

        return model;
    }
}
