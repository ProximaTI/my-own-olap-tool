/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableEditBean.java /main/2 2009/03/19 21:37:43 teclee Exp $ */

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
    dahmed      08/27/08 - 
    ahadi       06/12/08 - new PT edit model
    ahadi       06/12/08 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/PivotTableEditBean.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.pivotTable;

import oracle.adf.view.faces.bi.model.DataModel;

import oracle.adf.view.rich.model.ListOfValuesModel;

import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableComboboxLOVModel;
import oracle.adfdemo.view.feature.rich.pivotTable.data.PivotTableEditModel;

public class PivotTableEditBean {
    public PivotTableEditBean() {
        _dataModel = new PivotTableEditModel();
        _lovModel = new PivotTableComboboxLOVModel();
    }
    
    public ListOfValuesModel getListOfValuesModel(){
        return _lovModel.getListOfValuesModel();
    }
    
    public DataModel getDataModel() {
        return _dataModel;
    }
    
    //////////////////////////////////////////////////////////////////////////
    //PivotTableEditBean class attributes    
    PivotTableEditModel _dataModel; // data model
    PivotTableComboboxLOVModel _lovModel; // combobox lov model        
}


