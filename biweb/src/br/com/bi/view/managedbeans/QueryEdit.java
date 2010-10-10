package br.com.bi.view.managedbeans;


import br.com.bi.model.QueryFacade;
import br.com.bi.model.entity.Entity;
import br.com.bi.model.entity.queries.Node;
import br.com.bi.model.entity.queries.Query;
import br.com.bi.view.Util;
import br.com.bi.view.managedbeans.util.AbstractEditingBean;
import br.com.bi.view.managedbeans.util.CadListener;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.SQLException;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.dss.util.DataException;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

import org.olap4j.OlapException;
import org.olap4j.metadata.Cube;


public class QueryEdit extends AbstractEditingBean implements CadListener,
                                                              CubeMetadataListener {
    public static final String QUERY_EDIT_ACTION = "queryEdit";

    private TreeModel rowsTreeModel;
    private TreeModel columnsTreeModel;
    private Node selectedRowNode;
    private Node selectedColumnNode;
    private RichCommandButton refreshButton;
    private String result;

    /**
     * @return
     */
    public String getRowsNodeTitle() {
        Node node = getActualNode();

        if (node.getParentNode() == null && node.getChildren().size() == 0) {
            return getValueFromBundleByKey("ARRASTE_SOLTE_NIVEL");
        } else {
            return node.getName();
        }
    }

    /**
     * @return
     */
    public String getColumnsNodeTitle() {
        Node node = getActualNode();

        if (node.getParentNode() == null && node.getChildren().size() == 0) {
            return getValueFromBundleByKey("ARRASTE_SOLTE_NIVEL");
        } else {
            return node.getName();
        }
    }

    /**
     * @return
     */
    public String getNodeImage() {
        if (getActualNode().isLevel()) {
            return "/images/level.gif";
        } else if (getActualNode().isMeasure()) {
            return "/images/measure.gif";
        } else if (getActualNode().isMember()) {
            return "/images/member.gif";
        }
        return null;
    }

    /**
     * Retorna o modelo das linhas da consulta.
     * @return
     */
    public TreeModel getRowsTreeModel() {
        if (rowsTreeModel == null) {
            rowsTreeModel =
                    new ChildPropertyTreeModel(getRowsAxis(), "children");
        }
        return rowsTreeModel;
    }

    /**
     * Retorna o modelo das colunas da consulta.
     * @return
     */
    public TreeModel getColumnsTreeModel() {
        if (columnsTreeModel == null) {
            columnsTreeModel =
                    new ChildPropertyTreeModel(getColumnAxis(), "children");
        }
        return columnsTreeModel;
    }

    // ===================================================
    // = Eventos e métodos relacionados ao drag and drop =
    // ===================================================

    /**
     * Trata do evento soltar um item da árvore de dimensões e níveis do cubo
     * em cima da árvore das linhas.
     * @param dropEvent
     * @return
     * @throws OlapException
     * @throws CloneNotSupportedException
     * @throws SQLException
     * @throws DataException
     */
    public DnDAction droppedNodeInRows(DropEvent dropEvent) throws OlapException,
                                                                   CloneNotSupportedException,
                                                                   SQLException,
                                                                   DataException {
        doDrop(dropEvent);
        invalidateRowsTreeModel();
        return DnDAction.COPY;
    }

    private void doDrop(DropEvent dropEvent) throws CloneNotSupportedException {
        Node draggedNode = (Node)returnDraggedNode(dropEvent).clone();

        draggedNode.getChildren().clear();

        Node droppedNode = returnDroppedNode(dropEvent);

        draggedNode.setIndice(droppedNode.getChildren().size());

        droppedNode.addChild(draggedNode);
    }

    /**
     * Trata do evento soltar um item da árvore de dimensões e níveis do cubo
     * em cima da árvore das colunas.
     * @param dropEvent
     * @return
     * @throws OlapException
     * @throws CloneNotSupportedException
     * @throws SQLException
     * @throws DataException
     */
    public DnDAction droppedNodeInColumns(DropEvent dropEvent) throws CloneNotSupportedException,
                                                                      OlapException,
                                                                      SQLException,
                                                                      DataException {
        doDrop(dropEvent);
        invalidateColumnsTreeModel();
        return DnDAction.COPY;
    }

    /**
     * Retorna a referência para o nó arrastado.
     * @param dropEvent
     * @return
     */
    private Node returnDraggedNode(DropEvent dropEvent) {
        Transferable draggedTransferObject = dropEvent.getTransferable();
        DataFlavor<RowKeySet> draggedRowKeySetFlavor =
            DataFlavor.getDataFlavor(RowKeySet.class, "node");
        RowKeySet draggedRowKeySet =
            draggedTransferObject.getData(draggedRowKeySetFlavor);

        if (draggedRowKeySet != null) {
            CollectionModel treeModel =
                draggedTransferObject.getData(CollectionModel.class);

            Object draggedKey = draggedRowKeySet.iterator().next();
            treeModel.setRowKey(draggedKey);

            return (Node)treeModel.getRowData();
        }
        return null;
    }

    /**
     * Retorna referência para o nó sob o qual está sendo soltado o nó arrastado.
     * @param dropEvent
     * @return
     */
    private Node returnDroppedNode(DropEvent dropEvent) {
        Object serverRowKey = dropEvent.getDropSite();
        RichTree richTree = (RichTree)dropEvent.getDropComponent();

        richTree.setRowKey(serverRowKey);

        int rowIndex = richTree.getRowIndex();

        return (Node)richTree.getRowData(rowIndex);
    }

    // =============================================================================
    //  = Eventos e métodos relacionados à seleção das árvores de linhas e colunas =
    // =============================================================================

    /**
     * Retorna o nó selecionado nas linhas.
     * @return
     */
    public Node getSelectedNodeOnRows() {
        return selectedRowNode;
    }

    /**
     * Diz se o nó selecionado nas linhas é a raiz.
     * @return
     */
    public boolean isSelectedNodeOnRowsRoot() {
        return selectedRowNode != null &&
            selectedRowNode.getParentNode() == null;
    }

    /**
     * Retorna o nó selecionado nas colunas.
     * @return
     */
    public Node getSelectedNodeOnColumns() {
        return selectedColumnNode;
    }

    /**
     * Diz se o nó selecionado nas colunas é a raiz.
     * @return
     */
    public boolean isSelectedNodeOnColumnsRoot() {
        return selectedColumnNode != null &&
            selectedColumnNode.getParentNode() == null;
    }

    /**
     * Diz se o nó ligado ao bean "node" é a raiz.
     * @return
     */
    public boolean isActualNodeRoot() {
        return getActualNode().getName().equals(getValueFromBundleByKey("ARRASTE_SOLTE_NIVEL"));
    }

    /**
     * Retorna o nó que está ligado ao bean "node".
     * @return
     */
    public Node getActualNode() {
        return (Node)Util.getManagedBean("node");
    }

    /**
     * @param actionEvent
     * @throws OlapException
     * @throws SQLException
     * @throws DataException
     */
    public void removeNodeFromRows(ActionEvent actionEvent) throws OlapException,
                                                                   SQLException,
                                                                   DataException {
        Node node = getSelectedNodeOnRows();

        if (node.getParentNode() != null) {
            node.getParentNode().getChildren().remove(node);
        }
        invalidateRowsTreeModel();
    }

    /**
     * @param actionEvent
     * @throws OlapException
     * @throws SQLException
     * @throws DataException
     */
    public void removeNodeFromColumns(ActionEvent actionEvent) throws OlapException,
                                                                      SQLException,
                                                                      DataException {
        Node node = getSelectedNodeOnColumns();

        if (node.getParentNode() != null) {
            node.getParentNode().getChildren().remove(node);
        }
        invalidateColumnsTreeModel();
    }

    private void invalidateRowsTreeModel() throws OlapException, SQLException,
                                                  DataException {
        rowsTreeModel = null;
        invalidateQueryModel();
    }

    private void invalidateColumnsTreeModel() throws OlapException,
                                                     SQLException,
                                                     DataException {
        columnsTreeModel = null;
        invalidateQueryModel();
    }

    /**
     * @param selectionEvent
     */
    public void axisNodeSelectedInRows(SelectionEvent selectionEvent) {
        Object selectedKey = selectionEvent.getAddedSet().iterator().next();
        getRowsTreeModel().setRowKey(selectedKey);

        selectedRowNode = (Node)getRowsTreeModel().getRowData();
    }

    /**
     * @param selectionEvent
     */
    public void axisNodeSelectedInColumns(SelectionEvent selectionEvent) {
        Object selectedKey = selectionEvent.getAddedSet().iterator().next();
        getColumnsTreeModel().setRowKey(selectedKey);

        selectedColumnNode = (Node)getColumnsTreeModel().getRowData();
    }

    // =============================
    // = Processamento da consulta =
    // =============================

    private void invalidateQueryModel() throws OlapException, SQLException,
                                               DataException {
        result = null;
        ActionEvent event = new ActionEvent(refreshButton);
        event.queue();
    }

    /**
     * @param actionEvent
     */
    public void refreshQueryResult(ActionEvent actionEvent) {
        // Add event code here...
    }

    /**
     * @param refreshButton
     */
    public void setRefreshButton(RichCommandButton refreshButton) {
        this.refreshButton = refreshButton;
    }

    /**
     * @return
     */
    public RichCommandButton getRefreshButton() {
        return refreshButton;
    }

    /**
     * @param popupFetchEvent
     */
    public void contextMenuRowsFetched(PopupFetchEvent popupFetchEvent) {
        try {
            selectedRowNode = (Node)rowsTreeModel.getRowData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param popupFetchEvent
     */
    public void contextMenuColumnsFetched(PopupFetchEvent popupFetchEvent) {
        try {
            selectedColumnNode = (Node)columnsTreeModel.getRowData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public Node getRowsAxis() {
        if (getQuery().getRowsAxis() == null) {
            getQuery().setRowsAxis(new Node());
            getQuery().getRowsAxis().setName(getValueFromBundleByKey("LINHAS"));
        }

        return getQuery().getRowsAxis();
    }

    /**
     * @return
     */
    public Node getColumnAxis() {
        if (getQuery().getColumnsAxis() == null) {
            getQuery().setColumnsAxis(new Node());
            getQuery().getColumnsAxis().setName(getValueFromBundleByKey("COLUNAS"));
        }

        return getQuery().getColumnsAxis();
    }

    /**
     * @param actionEvent
     * @throws OlapException
     * @throws SQLException
     * @throws DataException
     */
    public void nonEmptySelectedOnRows(ActionEvent actionEvent) throws OlapException,
                                                                       SQLException,
                                                                       DataException {
        getQuery().setRowsAxisNonEmpty(!getQuery().isRowsAxisNonEmpty());
        invalidateQueryModel();
    }

    public void nonEmptySelectedOnColumns(ActionEvent actionEvent) throws OlapException,
                                                                          SQLException,
                                                                          DataException {
        getQuery().setColumnsAxisNonEmpty(getQuery().isColumnsAxisNonEmpty());
        invalidateQueryModel();
    }

    public String getResult() {
        if (result == null && getQuery().getCubeName() != null)
            try {
                result =
                        QueryFacade.getInstance().executeMdx(QueryFacade.getInstance().translateToMdx(getQuery()));
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                return sw.toString();
            }
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Query getQuery() {
        return (Query)getEntity();
    }

    public void init() {
        rowsTreeModel = null;
        columnsTreeModel = null;
        selectedRowNode = null;
        selectedColumnNode = null;
        result = null;
    }

    /**
     * @return
     */
    public String getTitle() {
        if (!getQuery().isPersisted())
            return getValueFromBundleByKey("CRIANDO_NOVA_CONSULTA");
        else
            return getValueFromBundleByKey("ALTERANDO_CONSULTA");
    }

    /**
     * @param entity
     */
    public void save(Entity entity) {
        QueryFacade.getInstance().saveQuery(getQuery());
    }

    // =============================
    // ======= Cad listener ========
    // =============================

    /**
     * Responde pela solicitação da inclusão de um novo objeto.
     * @return
     */
    public String insert() {
        setEntity(new Query());
        return QUERY_EDIT_ACTION;
    }

    /**
     * Responde pela solicitação da edição de um objeto já persistido.
     * @param entity
     * @return
     */
    public String edit(Entity entity) {
        setEntity(entity);
        return QUERY_EDIT_ACTION;
    }

    /**
     * Notificação da mudança do cubo selecionado.
     * @param selectedCube
     */
    public void selectedCubeChanged(Cube selectedCube) {
        if (selectedCube != null)
            getQuery().setCubeName(selectedCube.getName());

        init();
    }
}
