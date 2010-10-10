/* $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/MemberInterfaceImpl.java /main/2 2009/03/19 21:37:43 teclee Exp $ */

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
    bmoroze     11/14/07 - 
    dahmed      05/07/07 - .\
    bmoroze     09/15/06 - Creation
 */

/**
 *  @version $Header: dsstools/modules/dvt-faces-demo/src/oracle/adfdemo/view/feature/rich/pivotTable/data/dataSource/rowset/MemberInterfaceImpl.java /main/2 2009/03/19 21:37:43 teclee Exp $
 *  @author  bmoroze 
 *  @since   release specific (what release of product did this appear in)
 */
package oracle.adfdemo.view.feature.rich.pivotTable.data.dataSource.rowset;

import oracle.dss.util.DataDirector;
import oracle.dss.util.transform.MemberInterface;
import oracle.dss.util.transform.MemberMetadata;
import oracle.dss.util.transform.TransformException;


public class MemberInterfaceImpl implements MemberInterface
    {
        protected String m_value = null;
        
        public MemberInterfaceImpl(String value)
        {
            super();
            m_value = value;
        }
        
        // Compare two members based on the getValue()
        public boolean equals(MemberInterface member)
        {
            try
            {
                return m_value.equals(member.getValue());
            }
            catch (TransformException e)
            {
                return false;
            }
        }

        
        // Generate a hash code for this member based on the getValue()
        public int hashCode()
        {
            return m_value == null ? 0 : m_value.hashCode();
        }
        
        // Represent this member as its getValue() String
        public String toString()
        {
            return m_value.toString();
        }
        
        // Return the basic value used as the unique key for this member (i.e., MetadataMap.METADATA_VALUE)
        public String getValue() throws TransformException
        {
            return m_value;
        }
        
        // Return the appropriate member metadata for the given MetadataMap type
        public Object getMetadata(String type) throws TransformException
        {
            if (type.equals(MemberMetadata.COLLAPSED))
                return Boolean.valueOf(false);
            if (type.equals(MemberMetadata.AGGREGATE_POSITION))
                return AggregatePosition.NONE;
            if(type.equals(MemberMetadata.DRILLSTATE)) {
                return DataDirector.DRILLSTATE_NOT_DRILLABLE;
            }
            return m_value;
        }

    }
    
