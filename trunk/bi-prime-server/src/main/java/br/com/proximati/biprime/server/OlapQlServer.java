/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server;

import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.server.olapql.language.query.translator.QuerySqlTranslator;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableNode;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;

/**
 *
 * @author luiz
 */
public class OlapQlServer {

    public ResultSet openResultset(String olapql) throws Exception {
        DataSource dataSource = (DataSource) Application.getBean("dataSource");

        QuerySqlTranslator translator = new QuerySqlTranslator();

        return dataSource.getConnection().createStatement().executeQuery(translator.translateOlapQlInstruction(olapql));
    }

    public PivotTableModel execute(String olapql) throws Exception {
        DataSource dataSource = (DataSource) Application.getBean("dataSource");

        QuerySqlTranslator translator = new QuerySqlTranslator();

        ResultSet rs = dataSource.getConnection().createStatement().executeQuery(translator.translateOlapQlInstruction(olapql));

        HashMap<String, PivotTableNode> nodesMap = new HashMap<String, PivotTableNode>();

        while (rs.next()) {
            // extrai todos os nós do resultado
            Set<PivotTableNode> nodes = extractNodes(rs);

            // extrai desses nós os caminhos que levam aos dados nas linhas (pode ser mais de um. e.g um nó abaixo do outro na consulta)
            List<List<PivotTableNode>> rowNodes = extractRowPathNodes(nodes);
            // extrai desses nós os caminhos que levam aos dados nas colunas (pode ser mais de um. e.g um nó abaixo do outro na consulta)
            List<List<PivotTableNode>> columnNodes = extractColumnPathNodes(nodes);

            // realiza um produto cartesiano a partir dos caminhas



        }
    }

    private Set<PivotTableNode> extractNodes(ResultSet rs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<List<PivotTableNode>> extractRowPathNodes(Set<PivotTableNode> nodes) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private List<List<PivotTableNode>> extractColumnPathNodes(Set<PivotTableNode> nodes) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
