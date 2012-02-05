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
import java.util.Collections;
import java.util.Comparator;
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

        int columnsHeight = calcAxisHeight(model.getColumnsRoot());
        int rowsHeight = calcAxisHeight(model.getRowsRoot());
        
        System.out.println("columnsHeight > " + columnsHeight);
        System.out.println("rowsHeight > " + rowsHeight);
        
        leftCorner.setColspan(rowsHeight);
        leftCorner.setRowspan(columnsHeight);
        table.addCell(leftCorner);
        
        render(table, model, columnsHeight, rowsHeight);
        
        document.add(table);
        document.close();
    }

    /**
     * Calcula a altura da árvore representando um eixo da consulta. O valor calculado é
     * relativo ao MMC das profundidades de todas as folhas. Isto, porque a altura de todos
     * os nós devem ser expressas por um número inteiro.
     * @param root
     * @return 
     */
    private int calcAxisHeight(Node root) {
        // calcula a altura da árvore de nós representando o eixo
        final Queue<Integer> heightsLeaves = new LinkedList<Integer>();
        
        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {
            
            public void visitingRoot(Node s) {
            }
            
            public void visitingLeaf(Node s, Node u) {
                heightsLeaves.add(u.getDistanceUntilRoot());
            }
            
            public void visitingNonLeaf(Node s, Node v) {
            }
        });
        bfs.perform(root);
        
        int heightLCM = 1;
        while (heightsLeaves.size() > 0)
            heightLCM = MathUtils.lcm(heightLCM, heightsLeaves.poll());
        
        return heightLCM;
    }

    /**
     * @param table
     * @param context
     * @throws BadElementException
     */
    private void render(final Table table, final QueryResultModel model, final int columnsHeight, final int rowsHeight) throws BadElementException {
        final List<Node> columnLeafs = new ArrayList<Node>();
                
        BreadthFirstSearch bfs = new BreadthFirstSearch(new TraversingListener() {
            
            public void visitingRoot(Node s) {
            }
            
            public void visitingLeaf(Node s, Node u) {
                int height = columnsHeight / (u.getDistanceToDeeperLeaf() + u.getDistanceUntilRoot());
                Cell cell = new Cell(u.getValue().toString());
                cell.setRowspan(height);
                table.addCell(cell);
                
                columnLeafs.add(u);
                
                System.out.println(u.getValue().toString() + ">" + height);
            }
            
            public void visitingNonLeaf(Node s, Node v) {
                int height = columnsHeight / (v.getDistanceToDeeperLeaf() + v.getDistanceUntilRoot());
                Cell cell = new Cell(v.getValue().toString());
                cell.setColspan(v.getBreadth());
                cell.setRowspan(height);
                table.addCell(cell);
                
                System.out.println(v.getValue().toString() + ">" + height);
            }
        });
        
        bfs.perform(model.getColumnsRoot());
        
        Collections.sort(columnLeafs, new Comparator<Node>() {
            
            public int compare(Node t, Node t1) {
                return t.getPosition().compareTo(t1.getPosition());
            }
        });
        
        for (Node columnLeaf : columnLeafs)
            System.out.print(columnLeaf.getValue().toString() + " ");
        
        System.out.println("");
        
        DepthFirstSearch dfs = new DepthFirstSearch(new TraversingListener() {
            
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
