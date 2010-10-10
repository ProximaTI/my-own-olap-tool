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

import javax.faces.convert.NumberConverter;

import oracle.adf.view.faces.bi.component.pivotTable.CellFormat;
import oracle.adf.view.faces.bi.component.pivotTable.DataCellContext;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.dss.util.DataException;

import oracle.dss.util.QDR;

import oracle.dss.util.QDRMember;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset.RowsetDataSource;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableTotalsBean.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableTotalsBean {

    public PivotTableTotalsBean() {
        m_dm = new PivotTableSampleModel();
        
        // Enable totals
        m_dm.getRowsetDataSource().setTotalsEnabled(true);
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }

    private class TotalsCellFormat extends CellFormat {
         public TotalsCellFormat(DataCellContext dataCellContext){ 
             _dataCellContext = dataCellContext;
         }
         
         public String getTextStyle() {
             int totalMemberCounter = 0;
             for (Enumeration en = _dataCellContext.getQDR().getDimensions();en.hasMoreElements();) {
                 if (_dataCellContext.getQDR().getDimMember((String)en.nextElement()).getType() == QDRMember.TOTAL)
                    totalMemberCounter++;
             }
             String _textStyle = "";
             switch (totalMemberCounter) {
                case 0:
                  _textStyle = "";
                break;
                case 1:
                 _textStyle = "font-weight: bold;";
                break;
                default: // More than 1 Member Total
                 _textStyle = "font-weight: bold; font-style: italic;";
                break;
             }
             
             return _textStyle;
         }
         DataCellContext _dataCellContext;
    }

    public CellFormat getDataFormat(DataCellContext cxt) {
        CellFormat cellFormat = new TotalsCellFormat(cxt);
        return cellFormat;
    }

    UIPivotTable m_pivotTable = null;
    PivotTableSampleModel m_dm = null;
    private NumberConverter m_converter = null;
}
