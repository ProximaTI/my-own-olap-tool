/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.view.itext;

import br.com.proximati.biprime.util.BreadthFirstSearch;
import br.com.proximati.biprime.util.TraversingListener;
import br.com.proximati.biprime.server.olapql.query.result.QueryResultModel;
import br.com.proximati.biprime.server.olapql.query.result.Node;
import br.com.proximati.biprime.util.DepthFirstSearch;
import br.com.proximati.biprime.util.Pair;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter;
import com.lowagie.text.xml.XmlWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.math.util.MathUtils;

/**
 *
 * @author luiz
 */
public class QueryResultRenderer {

    /**
     * Renderiza um modelo de pivottable em um documento itext.
     * @param model
     * @param document
     * @throws BadElementException
     * @throws DocumentException
     */
    private void render(QueryResultModel model, Document document) throws BadElementException, DocumentException {
        document.open();

        Table table = new Table(model.getRowsRoot().getDistanceToDeeperLeaf() + model.getColumnsRoot().getBreadth());

        Cell leftCorner = new Cell();
        leftCorner.setColspan(model.getRowsRoot().getDistanceToDeeperLeaf());
        leftCorner.setRowspan(model.getColumnsRoot().getDistanceToDeeperLeaf());
        table.addCell(leftCorner);

        render(table, model);

        document.add(table);
        document.close();
    }

    /**
     * @param table
     * @param context
     * @throws BadElementException
     */
    private void render(final Table table, final QueryResultModel model) throws BadElementException {
        final List<Node> columnLeafs = new ArrayList<Node>();

        // calcula a altura da árvore de nós representando o eixo das colunas
        final Queue<Integer> heightsLeavesColumns = new LinkedList<Integer>();

        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {

            public void visitingRoot(Node s) {
            }

            public void visitingLeaf(Node s, Node u) {
                heightsLeavesColumns.add(u.getDistanceUntilRoot());
            }

            public void visitingNonLeaf(Node s, Node v) {
            }
        });
        bfs.perform(model.getColumnsRoot());

        int columnsHeightLCM = 1;
        while (heightsLeavesColumns.size() > 0)
            columnsHeightLCM = MathUtils.lcm(columnsHeightLCM, heightsLeavesColumns.poll());

        final int columnsHeight = columnsHeightLCM;

        bfs = new BreadthFirstSearch(new TraversingListener() {

            public void visitingRoot(Node s) {
            }

            public void visitingLeaf(Node s, Node u) {
                int height = columnsHeight / (u.getDistanceToDeeperLeaf() + u.getDistanceUntilRoot());
                Cell cell = new Cell(u.getValue().toString());
                cell.setRowspan(height);
                table.addCell(cell);

                columnLeafs.add(u);
            }

            public void visitingNonLeaf(Node s, Node v) {
                int height = columnsHeight / (v.getDistanceToDeeperLeaf() + v.getDistanceUntilRoot());
                Cell cell = new Cell(v.getValue().toString());
                cell.setColspan(v.getBreadth());
                cell.setRowspan(height);
                table.addCell(cell);
            }
        });

        bfs.perform(model.getColumnsRoot());

        /* =================== */
        final Queue<Integer> heightsLeavesRows = new LinkedList<Integer>();

        DepthFirstSearch dfs = new DepthFirstSearch(new TraversingListener() {

            public void visitingRoot(Node s) {
            }

            public void visitingLeaf(Node s, Node u) {
                heightsLeavesRows.add(u.getDistanceUntilRoot());
            }

            public void visitingNonLeaf(Node s, Node v) {
            }
        });

        dfs.perform(model.getRowsRoot());

        int rowsHeightLCM = 1;
        while (heightsLeavesRows.size() > 0)
            rowsHeightLCM = MathUtils.lcm(rowsHeightLCM, heightsLeavesRows.poll());

        final int rowsHeight = rowsHeightLCM;

        dfs = new DepthFirstSearch(new TraversingListener() {

            public void visitingRoot(Node s) {
            }

            public void visitingLeaf(Node s, Node u) {
                int height = rowsHeight / (u.getDistanceToDeeperLeaf() + u.getDistanceUntilRoot());

                Cell cell = new Cell(u.getValue().toString());
                cell.setColspan(height);
                table.addCell(cell);

                for (Node l : columnLeafs) {
                    Pair<Node, Node> pair =
                            new Pair<Node, Node>(u, l);

                    Cell value;
                    if (model.getValues().get(pair) != null)
                        value = new Cell(model.getValues().get(pair).toString());
                    else
                        value = new Cell("-");

                    table.addCell(value);
                }
            }

            public void visitingNonLeaf(Node s, Node v) {
                int height = rowsHeight / (v.getDistanceToDeeperLeaf() + v.getDistanceUntilRoot());

                Cell cell = new Cell(v.getValue().toString());
                cell.setRowspan(v.getBreadth());
                cell.setColspan(height);
                table.addCell(cell);
            }
        });

        dfs.perform(model.getRowsRoot());
    }

    /**
     *
     * @param pivotModel
     * @param output
     * @throws BadElementException
     * @throws DocumentException
     */
    public void renderAsHtml(QueryResultModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
        Document document = new Document();
        HtmlWriter.getInstance(document, output);
        render(pivotModel, document);
    }

    /**
     *
     * @param pivotModel
     * @param output
     * @throws BadElementException
     * @throws DocumentException
     */
    public void renderAsPdf(QueryResultModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        render(pivotModel, document);
    }

    /**
     *
     * @param pivotModel
     * @param output
     * @throws BadElementException
     * @throws DocumentException
     */
    public void renderAsRtf(QueryResultModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
        Document document = new Document();
        RtfWriter.getInstance(document, output);
        render(pivotModel, document);
    }

    /**
     *
     * @param pivotModel
     * @param output
     * @throws BadElementException
     * @throws DocumentException
     */
    public void renderAsXml(QueryResultModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
        Document document = new Document();
        XmlWriter.getInstance(document, output);
        render(pivotModel, document);
    }
}
