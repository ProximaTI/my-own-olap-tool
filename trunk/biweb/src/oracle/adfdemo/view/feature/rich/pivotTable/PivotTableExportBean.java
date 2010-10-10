/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableExportBean.java /main/1 2009/07/14 18:59:08 srkalyan Exp $ */

/* Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved. */

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    hbroek      06/30/09 - Additional Shepherd samples
    hbroek      06/26/09 - Added PivotTable samples for Export and stamped with
                           Gauges and SparkCharts
    hbroek      06/26/09 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableExportBean.java /main/1 2009/07/14 18:59:08 srkalyan Exp $
 *  @author  hbroek  
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable;

import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;

import oracle.dss.util.DataException;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableSampleModel;

public class PivotTableExportBean {

    public PivotTableExportBean() {
        m_dm = new PivotTableSampleModel();
    }
        
    public PivotTableSampleModel getDataModel() {
        return m_dm;
    }
    
    public UIPivotTable getPivotTable() {
        return m_pivotTable;
    }
    
    UIPivotTable m_pivotTable = null;
    PivotTableSampleModel m_dm = null;
}



