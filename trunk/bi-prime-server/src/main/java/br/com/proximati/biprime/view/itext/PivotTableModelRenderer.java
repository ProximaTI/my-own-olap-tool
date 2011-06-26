/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.view.itext;

import br.com.proximati.biprime.util.BreadthFirstSearch;
import br.com.proximati.biprime.util.TraversingListener;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableModel;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableNode;
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
import java.util.List;

/**
 *
 * @author luiz
 */
public class PivotTableModelRenderer {

    /**
     * Renderiza um modelo de pivottable em um documento itext.
     * @param model
     * @param document
     * @throws BadElementException
     * @throws DocumentException
     */
    private void render(PivotTableModel model, Document document) throws BadElementException, DocumentException {
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
    private void render(final Table table, final PivotTableModel model) throws BadElementException {
        final List<PivotTableNode> columnLeafs = new ArrayList<PivotTableNode>();

        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {

            public void visitingRoot(PivotTableNode s) {
            }

            public void visitingLeaf(PivotTableNode s, PivotTableNode u) {
                Cell cell = new Cell(u.getValue().toString());
                table.addCell(cell);

                columnLeafs.add(u);
            }

            public void visitingNonLeaf(PivotTableNode s, PivotTableNode v) {
                Cell cell = new Cell(v.getValue().toString());
                cell.setColspan(v.getBreadth());
                table.addCell(cell);
            }
        });

        bfs.perform(model.getColumnsRoot());


        DepthFirstSearch dfs = new DepthFirstSearch(new TraversingListener() {

            public void visitingRoot(PivotTableNode s) {
            }

            public void visitingLeaf(PivotTableNode s, PivotTableNode u) {
                Cell cell = new Cell(u.getValue().toString());
                table.addCell(cell);

                for (PivotTableNode l : columnLeafs) {
                    Pair<PivotTableNode, PivotTableNode> pair =
                            new Pair<PivotTableNode, PivotTableNode>(u, l);

                    Cell value;
                    if (model.getValues().get(pair) != null)
                        value = new Cell(model.getValues().get(pair).toString());
                    else
                        value = new Cell("-");

                    table.addCell(value);
                }
            }

            public void visitingNonLeaf(PivotTableNode s, PivotTableNode v) {
                Cell cell = new Cell(v.getValue().toString());
                cell.setRowspan(v.getBreadth());
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
    public void renderAsHtml(PivotTableModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
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
    public void renderAsPdf(PivotTableModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
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
    public void renderAsRtf(PivotTableModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
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
    public void renderAsXml(PivotTableModel pivotModel, OutputStream output) throws BadElementException, DocumentException {
        Document document = new Document();
        XmlWriter.getInstance(document, output);
        render(pivotModel, document);
    }
}
