/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.view.itext;

import br.com.proximati.biprime.server.olapql.query.result.PivotTableModel;
import br.com.proximati.biprime.server.olapql.query.result.PivotTableNode;
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
import java.awt.Point;
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

        int rowsAxisHeight = branchHeight(model.getRowsRoot());
        int columnsAxisWidth = branchWidth(model.getColumnsRoot());
        int columnsAxisHeight = branchHeight(model.getColumnsRoot());

        // a largura da tabela é determinada pela altura da árvore
        // de nós do eixo das linhas somada à largura da árvore de nós
        // do eixo das colunas
        Table table = new Table(rowsAxisHeight + columnsAxisWidth);

        Cell leftCorner = new Cell();
        leftCorner.setColspan(rowsAxisHeight);
        leftCorner.setRowspan(columnsAxisHeight);
        table.addCell(leftCorner);

        RenderingContext columnsRenderingContext = new RenderingContext(model, model.getColumnsRoot(), rowsAxisHeight);
        renderColumns(table, columnsRenderingContext);
        renderRows(table, new RenderingContext(model, model.getRowsRoot(), columnsAxisHeight), columnsRenderingContext.getLeafs());

        document.add(table);

        document.close();
    }

    /**
     * Retorna a altura de um galho de uma árvore de nós.
     * @return
     */
    private int branchHeight(PivotTableNode branchRoot) {
        if (branchRoot.getChildrenNodes().isEmpty())
            return 1;
        else {
            int height = 0;
            for (PivotTableNode nome : branchRoot.getChildrenNodes())
                height = Math.max(height, branchHeight(nome));
            return 1 + height;
        }
    }

    /**
     * Retorna a largura de um galho de uma árvore de nós.
     * @return
     */
    private int branchWidth(PivotTableNode branchRoot) {
        if (branchRoot.getChildrenNodes().isEmpty())
            return 1;
        else {
            int leafs = 0;
            for (PivotTableNode n : branchRoot.getChildrenNodes())
                leafs += branchWidth(n);
            return leafs;
        }
    }

    /**
     * Renderiza o eixo das colunas em uma tabela itext.
     * @param table
     * @param context
     * @throws BadElementException
     */
    private void renderColumns(Table table, RenderingContext context) throws BadElementException {
        renderColumnNode(context.root, table, context);
    }

    /**
     * Renderiza o eixo das linhas em uma tabela itext.
     * @param table
     * @param context
     * @throws BadElementException
     */
    private void renderRows(Table table, RenderingContext context, List<PivotTableNode> columnLeafs) throws BadElementException {
        renderRowNode(context.root, table, context, columnLeafs);
    }

    /**
     * Renderiza um nó do da árvore de nós relacionada o eixo das colunas de
     * uma consulta.
     * @param node
     * @param table
     * @return
     * @throws BadElementException
     */
    private int renderColumnNode(PivotTableNode node, Table table,
            RenderingContext context) throws BadElementException {

        if (node.getParentNode() != null
                && node.getParentNode().getChildrenNodes().indexOf(node) > 0
                && context.getAddedNodesCountByDepth(context.depthOfCurrentNode) == 0)
            // se isto for verdade a árvore está desbalanceada, mais profunda à direita
            node = node;

        Point position = new Point(context.depthOfCurrentNode,
                context.getAddedNodesCountByDepth(context.depthOfCurrentNode));

        context.incrementAddedNodesCount(context.depthOfCurrentNode);

        if (node.getChildrenNodes().isEmpty()) {
            context.getLeafs().add(node);
            renderNode(node, table, position);
            return 1;
        } else {
            context.depthOfCurrentNode++;
            Cell cell = renderNode(node, table, position);
            int leafs = 0;
            for (PivotTableNode child : node.getChildrenNodes())
                leafs += renderColumnNode(child, table, context);
            cell.setColspan(leafs);
            context.depthOfCurrentNode--;
            return leafs;
        }
    }

    /**
     * Renderiza um nó do da árvore de nós relacionada o eixo das colunas de
     * uma consulta.
     * @param node
     * @param table
     * @return
     * @throws BadElementException
     */
    private int renderRowNode(PivotTableNode node, Table table,
            RenderingContext context, List<PivotTableNode> columnLeafs) throws BadElementException {

        Point position = new Point(
                context.getAddedNodesCountByDepth(context.depthOfCurrentNode),
                context.depthOfCurrentNode);

        context.incrementAddedNodesCount(context.depthOfCurrentNode);

        if (node.getChildrenNodes().isEmpty()) {
            renderNode(node, table, position);

            for (PivotTableNode columnLeaf : columnLeafs) {
                Pair<PivotTableNode, PivotTableNode> pair = new Pair<PivotTableNode, PivotTableNode>(
                        node, columnLeaf);
                Number number = context.getModel().getValues().get(pair);
                position.y++;
                if (number != null)
                    table.addCell(new Cell(number.toString()), position);
            }
            return 1;
        } else {
            context.depthOfCurrentNode++;
            Cell cell = renderNode(node, table, position);
            int leafs = 0;
            for (PivotTableNode child : node.getChildrenNodes())
                leafs += renderRowNode(child, table, context, columnLeafs);
            cell.setRowspan(leafs);
            context.depthOfCurrentNode--;
            return leafs;
        }
    }

    /**
     * Cria e devolve a referência para uma célula referente a um nó.
     * @param node
     * @param table
     * @param position
     * @return
     * @throws BadElementException
     */
    private Cell renderNode(PivotTableNode node, Table table, Point position)
            throws BadElementException {
        Cell cell = null;
        if (node.getValue() == null)
            cell = new Cell();
        else
            cell = new Cell(node.getValue().toString());

        if (position == null)
            table.addCell(cell);
        else
            table.addCell(cell, position);
        return cell;
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

    /**
     * Classe que encapsula o estado da renderização do eixo das colunas na
     * forma de um contexto.
     */
    class RenderingContext {

        /**
         * Recorda as folhas visitadas durante o processo de renderização.
         */
        private List<PivotTableNode> leafs = new ArrayList<PivotTableNode>();

        public List<PivotTableNode> getLeafs() {
            return leafs;
        }
        private int offset;
        /**
         * guarda referência para a raiz da árvore.
         */
        private PivotTableNode root;
        /**
         * guarda a profundidade do nó que está sendo renderizado.
         */
        private int depthOfCurrentNode;
        /**
         * guarda a quantidade de nós renderizadas em cada nível da árvore de nós.
         */
        private int[] addedNodesCount;
        private PivotTableModel model;

        public int getAddedNodesCountByDepth(int depth) {
            return addedNodesCount[depth] + offset;
        }

        public int incrementAddedNodesCount(int depth) {
            return addedNodesCount[depth]++;
        }

        public PivotTableModel getModel() {
            return model;
        }

        RenderingContext(PivotTableModel model, PivotTableNode root, int offset) {
            this.addedNodesCount = new int[branchHeight(root)];
            this.model = model;
            this.root = root;
            this.offset = offset;
        }
    }
}
