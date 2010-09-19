/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Logger;

import org.olap4j.OlapConnection;
import org.olap4j.OlapWrapper;


/**
 *
 * @author Luiz Augusto
 */
public class OlapConnectionHolder {

    private Connection jdbcConnection;
    private OlapConnection olapConnection;

    public OlapConnection getOlapConnection() {
        if (olapConnection == null) {
            try {
                olapConnection =
                        (OlapConnection)((OlapWrapper)getJdbcConnection()).unwrap(OlapConnection.class);
            } catch (SQLException ex) {
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe(ex.getMessage());
            }
        }
        return olapConnection;
    }

    public Connection getJdbcConnection() {
        if (jdbcConnection == null) {
            try {
                Class.forName("mondrian.olap4j.MondrianOlap4jDriver");

                // TODO Colocar os parâmetros de conexão em um arquivo de propriedades.
                jdbcConnection =
                        DriverManager.getConnection("jdbc:mondrian:Jdbc='jdbc:mysql://localhost:3306/foodmart';" +
                                                    "Catalog=file:/C:\\Users\\Luiz\\BI\\Ferramentas\\Mondrian Workbench\\schema-workbench\\demo\\FoodMart.xml;" +
                                                    "JdbcDrivers=com.mysql.jdbc.Driver;" +
                                                    "JdbcUser=foodmart;JdbcPassword=foodmart;");
            } catch (Exception ex) {
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe(ex.getMessage());
            }
        }
        return jdbcConnection;
    }

    public void invalidateConnection() {
        jdbcConnection = null;
        olapConnection = null;
    }
}
