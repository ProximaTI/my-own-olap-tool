/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.ui;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author luiz
 */
@Controller("queryHandler")
@Scope("view")
public class QueryHandler {

    private Logger logger = LoggerFactory.getLogger(QueryHandler.class);
    private String olapQl;
    @Autowired
    private DataSource dataSource;

    /**
     * @return the olapQl
     */
    public String getOlapQl() {
        return olapQl;
    }

    /**
     * @param olapQl the olapQl to set
     */
    public void setOlapQl(String olapQl) {
        this.olapQl = olapQl;
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
