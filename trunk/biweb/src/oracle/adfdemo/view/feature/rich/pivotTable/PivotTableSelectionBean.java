/* Copyright (c) 2007, 2009, Oracle and/or its affiliates. 
All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      11/20/08 - New Pivottable samples
    hbroek      11/20/08 - First version
    hbroek      08/10/23 - 
 */
package oracle.adfdemo.view.feature.rich.pivotTable;

import java.util.Enumeration;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;

import oracle.adf.view.faces.bi.component.pivotTable.DataCellRange;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.adf.view.faces.bi.model.DataCellIndex;

import javax.faces.event.ActionEvent;

import oracle.dss.util.DataAccess;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataException;

import oracle.dss.util.DataMap;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;

import oracle.dss.util.QDRMember;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset.RowsetDataSource;
// Commented out for dvt-adf integration work
//import oracle.dvtdemo.utils.tageditor.ComponentEditorHandler;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableSelectionBean.java /main/3 2009/04/24 14:30:33 teclee Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableSelectionBean {

    public PivotTableSelectionBean() {
        m_dm = new PivotTableSampleModel();
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public String getSelectedData(UIPivotTable pt) {
    
        if (pt.getSelection().getDataCells().size() == 0)
            return "No data selected";
        StringBuffer _buffer = new StringBuffer();
        
        DataAccess da = pt.getDataModel().getDataAccess();

        Set<DataCellRange> selectedData = pt.getSelection().getDataCells();
        DataCellRange range = selectedData.iterator().next();
        DataCellIndex start = range.getStartIndex(pt.getDataModel());
        DataCellIndex end = range.getEndIndex(pt.getDataModel());        
        int startRow = start.getRow();
        int startCol = start.getColumn();
        int endRow = (end==null) ? startRow : end.getRow();
        int endCol = (end==null) ? startCol : end.getColumn();
        
        try  {                         
            for(int r=startRow;r<=endRow;r++) {
                String rowLabel = (String)da.getSliceLabel(DataDirector.ROW_EDGE, r, MetadataMap.METADATA_LONGLABEL);
                for(int c=startCol;c<=endCol;c++) {
                    String colLabel = (String)da.getSliceLabel(DataDirector.COLUMN_EDGE, c, MetadataMap.METADATA_LONGLABEL);                        
                    Object value = da.getValue(r,c,DataMap.DATA_VALUE);
                    _buffer.append('[').append(colLabel).append(',').append(rowLabel).append(',').append(value).append(']').append('\n');
                }
            }
        } catch ( DataException e) {
            e.printStackTrace();
        }
        return _buffer.toString();
    }
    
    public void handleSelectedTask(ActionEvent evt)
    {
        UIComponent button  = (UIComponent)evt.getSource();
        UIComponent container  = button.getParent();
        UIPivotTable pt = null;
        for (UIComponent component: container.getChildren()) {
            if (component instanceof UIPivotTable) {
                pt= (UIPivotTable)component;
            }
        }
        if (pt != null)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(getSelectedData(pt)));
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't find PivotTable"));
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        m_pivotTable = pivotTable;
        
        // This call is only necessary when in the sample framework
        wireUpEditorToComponent(m_pivotTable);
    }
    
    
    /* Set the UIComponent on the Property Editor */
    private void wireUpEditorToComponent(UIComponent component) {
        /*FacesContext context = FacesContext.getCurrentInstance();
        ComponentEditorHandler editor = 
            (ComponentEditorHandler)context.getELContext().getELResolver().getValue(context.getELContext(), 
                                                                                    null, 
                                                                                    "editor");
        //Update Editor with UIComponent
        if (editor != null && !m_editorInitialized) {
          m_editorInitialized = true;
          editor.setComponent(component);
        }*/
    }

    boolean m_editorInitialized = false;
    PivotTableSampleModel m_dm = null;
    UIPivotTable m_pivotTable = null;
}
