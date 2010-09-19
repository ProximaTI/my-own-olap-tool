/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/CellInterfaceImpl.java /main/2 2009/03/19 21:37:43 teclee Exp $ */

/* Copyright (c) 2006, 2009, Oracle and/or its affiliates. 
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
    bmoroze     09/15/06 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/CellInterfaceImpl.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  bmoroze 
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import oracle.dss.util.transform.DataCellInterface;
import oracle.dss.util.transform.DataType;
import oracle.dss.util.transform.TransformException;

public class CellInterfaceImpl implements DataCellInterface
    {
        protected Object m_value = null;
        
        public CellInterfaceImpl(Object value)
        {
            m_value = value;
        }
        
        // Return the data value for the given DataMap type
        public Object getData(String type)
        {                
            return m_value;
        }

        public boolean setData(Object value, String type) throws TransformException
        {
            return false;
        }        
    }
    
