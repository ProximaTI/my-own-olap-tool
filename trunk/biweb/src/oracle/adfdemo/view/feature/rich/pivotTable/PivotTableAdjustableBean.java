/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableAdjustableBean.java /main/4 2009/04/24 14:30:33 teclee Exp $ */

/* Copyright (c) 2008, 2009, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    teclee      09/18/08 - 
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableAdjustableBean.java /main/4 2009/04/24 14:30:33 teclee Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.model.DataModel;

import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.dss.util.DataDirector;

import oracle.dss.util.EdgeOutOfRangeException;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableAdjustableModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableComboboxLOVModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableEditModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.fake.FakeDataSource;

// Commented out for dvt-adf integration work
//import oracle.dvtdemo.utils.tageditor.ComponentEditorHandler;

import org.apache.myfaces.trinidad.bean.FacesBean;

public class PivotTableAdjustableBean {
    public PivotTableAdjustableBean() {
        _dataModel = new PivotTableAdjustableModel();
    }
    
    public DataModel getDataModel() {
        return _dataModel;
    }

    /**
     * Resets component properties that can become invalid upon changing the datasource.
     * Resets the startRow and StartColumn properties to null, and clears the selection.  
     * This should be called by handlers that change the data source, change the shape of the data, etc.
     */
    private void resetPivotTableState() {
        UIPivotTable pivotTable = getPivotTable();
        if (pivotTable != null) {
            FacesBean bean = pivotTable.getFacesBean();
            bean.setProperty(UIPivotTable.START_ROW_KEY, null);
            bean.setProperty(UIPivotTable.START_COLUMN_KEY, null);
            pivotTable.getSelection().clear();
        }
    }

    public UIPivotTable getPivotTable() {
        FacesContext context = FacesContext.getCurrentInstance();
        /*ComponentEditorHandler editor = 
            (ComponentEditorHandler)context.getELContext().getELResolver().getValue(context.getELContext(), 
                                                                                    null, 
                                                                                    "editor");*/
        UIPivotTable pivotTable = null;
        /*if (editor != null)
        {
            if (editor.getUIComponent() instanceof UIPivotTable)
                pivotTable = ((UIPivotTable)editor.getUIComponent());
        }*/
        return pivotTable;
    }

    public void handleShapeChange(ActionEvent event) {
        resetPivotTableState();
    }

    public Integer getRowCount() {
        try {
            return _dataModel.getDataAccess().getEdgeExtent(DataDirector.ROW_EDGE);
        } catch (EdgeOutOfRangeException e) {
            assert false : "Unreachable: valid edge hardcoded.";
        }
        return 0; // unreachable, to placate compiler
    }

    public void setRowCount(Integer rowCount) {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setEdgeExtent(DataDirector.ROW_EDGE, rowCount);
    }

    public Integer getColumnCount() {
        try {
            return _dataModel.getDataAccess().getEdgeExtent(DataDirector.COLUMN_EDGE);
        } catch (EdgeOutOfRangeException e) {
            assert false : "Unreachable: valid edge hardcoded.";
        }
        return 0; // unreachable, to placate compiler
    }

    public void setColumnCount(Integer columnCount) {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setEdgeExtent(DataDirector.COLUMN_EDGE, columnCount);
    }

    public Integer getRowEdgeLayerCount() {
        try {
            return _dataModel.getDataAccess().getLayerCount(DataDirector.ROW_EDGE);
        } catch (EdgeOutOfRangeException e) {
            assert false : "Unreachable: valid edge hardcoded.";
        }
        return 0; // unreachable, to placate compiler
    }

    public void setRowEdgeLayerCount(Integer layerCount) {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setLayerCount(DataDirector.ROW_EDGE, layerCount);
    }

    public Integer getColumnEdgeLayerCount() {
        try {
            return _dataModel.getDataAccess().getLayerCount(DataDirector.COLUMN_EDGE);
        } catch (EdgeOutOfRangeException e) {
            assert false : "Unreachable: valid edge hardcoded.";
        }
        return 0; // unreachable, to placate compiler
    }

    public void setColumnEdgeLayerCount(Integer layerCount) {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setLayerCount(DataDirector.COLUMN_EDGE, layerCount);
    }

    public Integer getRowEdgeBranchingFactor() {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        return fds.getBranchingFactor(DataDirector.ROW_EDGE);
    }

    public void setRowEdgeBranchingFactor(Integer branchingFactor) {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setBranchingFactor(DataDirector.ROW_EDGE, branchingFactor);
    }

    public Integer getColumnEdgeBranchingFactor() {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        return fds.getBranchingFactor(DataDirector.COLUMN_EDGE);
    }

    public void setColumnEdgeBranchingFactor(Integer branchingFactor) {
        FakeDataSource fds = (FakeDataSource)(_dataModel.getDataSource());
        fds.setBranchingFactor(DataDirector.COLUMN_EDGE, branchingFactor);
    }
    
    PivotTableAdjustableModel _dataModel; // data model
}


