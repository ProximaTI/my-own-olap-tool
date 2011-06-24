/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.view.itext;

import br.com.proximati.biprime.util.BreadthFirstSearch;
import br.com.proximati.biprime.util.TraversingListener;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableModel;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableNode;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableNodeRoot;
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

        // a largura da tabela é determinada pela altura da árvore
        // de nós do eixo das linhas somada à largura da árvore de nós
        // do eixo das colunas
        Table table = new Table(model.getRowsRoot().getDistanceToDeeperLeaf() + 1 + model.getColumnsRoot().getBreadth());

        Cell leftCorner = new Cell();
        leftCorner.setColspan(model.getRowsRoot().getDistanceToDeeperLeaf() + 1);
        leftCorner.setRowspan(model.getColumnsRoot().getDistanceToDeeperLeaf() + 1);
        table.addCell(leftCorner);

        renderColumns(table, model.getColumnsRoot());
        renderRows(table, model.getRowsRoot());
        
        document.add(table);
        document.close();
    }

    /**
     * Renderiza o eixo das colunas em uma tabela itext.
     * @param table
     * @param context
     * @throws BadElementException
     */
    private void renderColumns(final Table table, PivotTableNodeRoot root) throws BadElementException {
        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {

            public void visitingRoot(PivotTableNodeRoot s) {
            }

            public void visitingLeaf(PivotTableNodeRoot s, PivotTableNode u) {
                Cell cell = new Cell(u.getValue().toString());
                table.addCell(cell);
            }

            public void visitingNonLeaf(PivotTableNodeRoot s, PivotTableNode v) {
                Cell cell = new Cell(v.getValue().toString());
                cell.setColspan(v.getBreadth());
                table.addCell(cell);
            }
        });
        bfs.perform(root);
    }

    /**
     * Renderiza o eixo das linhas em uma tabela itext.
     * @param table
     * @param root
     * @throws BadElementException
     */
    private void renderRows(final Table table, PivotTableNodeRoot root) throws BadElementException {

        // IMPLEMENTAR O CAMINHAMENTO DFS AQUI

        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {

            public void visitingRoot(PivotTableNodeRoot s) {
            }

            public void visitingLeaf(PivotTableNodeRoot s, PivotTableNode u) {
                Cell cell = new Cell(u.getValue().toString());
                table.addCell(cell);
            }

            public void visitingNonLeaf(PivotTableNodeRoot s, PivotTableNode v) {
                Cell cell = new Cell(v.getValue().toString());
                cell.setRowspan(v.getBreadth());
                table.addCell(cell);
            }
        });
        bfs.perform(root);
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
