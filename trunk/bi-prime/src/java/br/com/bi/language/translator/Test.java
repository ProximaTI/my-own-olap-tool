/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.translator;

import br.com.bi.language.query.ParseException;
import br.com.bi.language.query.QueryParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author luiz
 */
public class Test {

    public static void main(String[] args) {
        try {
            String olapql = "selecione ([Recursos Humanos], [Produto]) nas linhas, [Total] nas colunas do cubo [Vendas] onde [Recursos Humanos]";

            InputStream in = new ByteArrayInputStream((olapql).getBytes());

            QueryParser parser = new QueryParser(in);

            StringBuilder sb = new StringBuilder();
            QuerySqlTranslator translator = new QuerySqlTranslator();
            translator.visit(parser.instruction(), sb);

            System.out.println(sb.toString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
