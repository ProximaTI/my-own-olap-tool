/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableEditModel.java /main/2 2009/03/19 21:37:43 teclee Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableEditModel.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import java.util.ArrayList;

public class PivotTableEditModel extends BaseRowsetModel {
  public PivotTableEditModel() {
  }

  protected ArrayList<String[]> getRowsetData() {
      return getRowsetDataFromString(PivotTableEditModel.SAMPLE_ROWSET);
  }

  private static final String SAMPLE_ROWSET = 
        "Time, Product, Channel, Geography, Sales, Units, Available, Price, Color, Weight, Link, Size, Supply Date\n" +
        "row, row, row, row, data, data, data, data, data, data, data, data, data\n" +
        "string, string, string, string, double, double, string, double, string, double, string, string, date\n" +
        "2007, Tents, All Channels, World, 20000, 200, true, 33, red, 33, Main-link, L, 01/01/2000\n" +
        "2008, Jacket, All Channels, Cambridge, 500, 50, true, 44, milk, 44, Sub-link, M, 03/03/2003\n" +
        "2007, Tents, All Channels, Boston, 500, 50, false, 66, coffee, 66, Main-link, S, 04/04/2004";
    

}
