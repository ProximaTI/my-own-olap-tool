/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.utils;

import br.com.bi.olapql.language.query.translator.QuerySqlTranslator;
import br.com.bi.olapql.language.query.Instruction;
import br.com.bi.olapql.language.query.ParseException;
import br.com.bi.olapql.language.query.QueryParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author luiz
 */
public class Test {

    public static void main(String[] args) {
        try {
            String olapql =
                    "selecione ( [Região].[País], ( [Região].[Cidade], [Loja])) nas linhas, [Total] nas colunas do cubo [Vendas] onde [EUA]";

            InputStream in = new ByteArrayInputStream((olapql).getBytes());

            QueryParser parser = new QueryParser(in);
            Instruction instruction = (Instruction) parser.instruction();
            instruction.dump(" ");

            StringBuilder sb = new StringBuilder();
            QuerySqlTranslator translator = new QuerySqlTranslator();
            translator.visit(instruction, sb);

            System.out.println(sb.toString());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
