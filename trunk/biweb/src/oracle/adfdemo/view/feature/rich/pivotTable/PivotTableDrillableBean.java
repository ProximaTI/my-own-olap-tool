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

import oracle.adf.view.faces.bi.model.PivotTableModel;

import oracle.dss.util.DataException;

import oracle.dss.util.DataSource;
import oracle.dss.util.QDR;

import oracle.dss.util.QDRMember;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.AsymmetricDrillablePagingDataSource;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.custom.DrillablePagingDataSource;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.fake.FakeDataSource;
import oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset.RowsetDataSource;


/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableDrillableBean.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  hbroek
 *  @since   release specific (what release of product did this appear in)
 */
public class PivotTableDrillableBean {

    public PivotTableDrillableBean() {
        m_dm = new PivotTableDrillableModel();
    }
        
    public PivotTableModel getDataModel() {
        return m_dm;
    }
    
    private class PivotTableDrillableModel extends PivotTableModel {
          public PivotTableDrillableModel() {
//              m_ds = new DrillablePagingDataSource();
              m_ds = new AsymmetricDrillablePagingDataSource();
              setDataSource(m_ds);
          }
          
          DataSource m_ds = null;
    }
    
    PivotTableModel m_dm = null;
}
