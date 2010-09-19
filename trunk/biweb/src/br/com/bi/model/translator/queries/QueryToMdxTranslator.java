package br.com.bi.model.translator.queries;


import br.com.bi.model.entity.queries.Node;
import br.com.bi.model.entity.queries.Query;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.List;

import org.olap4j.Axis;
import org.olap4j.mdx.AxisNode;
import org.olap4j.mdx.CallNode;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.mdx.ParseTreeWriter;
import org.olap4j.mdx.Syntax;


/**
 * Classe responsável por traduzir uma consulta para uma instrução MDX.
 *
 * Baseado em um analisador preditivo usando descida recursiva.
 *
 * @author Luiz Augusto
 */
public class QueryToMdxTranslator {
    static final String CROSSJOIN = "Crossjoin";
    static final String BRACES = "{}";

    private Query query;

    public QueryToMdxTranslator(Query query) {
        this.query = query;
    }

    public String translate() {
        //Truque para retornar os membros enquanto a consulta está sendo
        //construída
        boolean brakeNonEmpty =
            query.getRowsAxis().getChildren().size() == 0 ||
            query.getColumnsAxis().getChildren().size() == 0;

        // =======================================================
        // = Transdução para a árvore sintática da linguagem mdx =
        // =======================================================

        AxisNode rowsNode =
            transduceAxis(query.getRowsAxis(), Axis.ROWS, query.isRowsAxisNonEmpty() &&
                          !brakeNonEmpty);

        AxisNode columnsNode =
            transduceAxis(query.getColumnsAxis(), Axis.COLUMNS,
                          query.isColumnsAxisNonEmpty() && !brakeNonEmpty);

        // ============================
        // = Geração da instrução mdx =
        // ============================

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ParseTreeWriter mdxWriter = new ParseTreeWriter(pw);

        sw.append("Select ");
        rowsNode.unparse(mdxWriter);
        pw.flush();
        sw.append(", ");
        columnsNode.unparse(mdxWriter);
        pw.flush();
        sw.append(" From ").append("[" + query.getCubeName() + "]");

        return sw.toString();
    }

    private AxisNode transduceAxis(Node node, Axis axis, boolean isNonEmpty) {
        List<ParseTreeNode> elements = new ArrayList<ParseTreeNode>();

        if (node.getChildren().size() > 1) {
            // constrói uma tupla
            for (Node child : node.getChildren()) {
                elements.add(transduceNode(child));
            }

            return new AxisNode(null, isNonEmpty, axis, null,
                                new CallNode(null, BRACES, Syntax.Braces,
                                             elements));
        } else if (node.getChildren().size() == 1) {
            return new AxisNode(null, isNonEmpty, axis, null,
                                transduceNode(node.getChildren().get(0)));
        }

        // se não tiver nada no eixo, retorna uma tupla vazia
        return new AxisNode(null, isNonEmpty, axis, null,
                            new CallNode(null, BRACES, Syntax.Braces,
                                         elements));
    }

    public ParseTreeNode transduceNode(Node node) {
        ParseTreeNode transducedNode = null;

        if (node.isLevel()) {
            transducedNode = Util.buildLevelNode(node);
        } else if (node.isMeasure()) {
            transducedNode = Util.buildMeasureNode(node);
        } else if (node.isMember()) {
            transducedNode = Util.buildMemberNode(node);
        }

        if (node.getChildren().size() == 1) {
            return buildCrossjoin(transducedNode,
                                  transduceNode(node.getChildren().get(0)));
        } else if (node.getChildren().size() > 1) {
            // constrói uma tupla
            List<ParseTreeNode> elements = new ArrayList<ParseTreeNode>();

            for (Node child : node.getChildren()) {
                elements.add(transduceNode(child));
            }

            ParseTreeNode tuple =
                new CallNode(null, BRACES, Syntax.Braces, elements);
            return buildCrossjoin(transducedNode, tuple);
        }
        return transducedNode;
    }

    private ParseTreeNode buildCrossjoin(ParseTreeNode left,
                                         ParseTreeNode right) {
        List<ParseTreeNode> arguments = new ArrayList<ParseTreeNode>();
        arguments.add(left);
        arguments.add(right);

        return new CallNode(null, CROSSJOIN, Syntax.Function, arguments);
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
