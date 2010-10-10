/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableSampleModel.java /main/2 2009/03/19 21:37:43 teclee Exp $ */

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
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/PivotTableSampleModel.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  ahadi   
 *  @since   release specific (what release of product did this appear in)
 */

package oracle.adfdemo.view.feature.rich.pivotTable.data;

import java.util.ArrayList;

public class PivotTableSampleModel extends BaseRowsetModel {
  public PivotTableSampleModel() {
  }

  protected ArrayList<String[]> getRowsetData() {
      return getRowsetDataFromString(PivotTableSampleModel.SAMPLE_ROWSET);
  }

  private static final String SAMPLE_ROWSET = 
        "Time, Product, Channel, Geography, Sales, Units\n" +
        "row, row, column, column, data, data\n" +
        "string, string, string, string, double, double\n" +
        "2007, Tents, All Channels, World, 20000, 200\n" +
        "2007, Tents, All Channels, Boston, 500, 50\n" +
        "2007, Canoes, All Channels, World, 15000, 75\n" +
        "2007, Canoes, All Channels, Boston, 1500, 8\n" +
        "2006, Tents, All Channels, World, 10000, 100\n" +
        "2006, Tents, All Channels, Boston, 250, 25\n" +
        "2006, Canoes, All Channels, World, 7500, 40\n" +
        "2006, Canoes, All Channels, Boston, 750, 4\n" +
        "2005, Tents, All Channels, World, 5000, 50\n" +
        "2005, Tents, All Channels, Boston, 125, 15\n" +
        "2005, Canoes, All Channels, World, 3750, 20\n" +
        "2005, Canoes, All Channels, Boston, 375, 2";

}
