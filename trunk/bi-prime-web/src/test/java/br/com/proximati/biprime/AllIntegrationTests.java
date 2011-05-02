/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime;


import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author carlos
 */
@RunWith(Suite.class)
//@Suite.SuiteClasses({AllIntegrationTests
//.class})
public class AllIntegrationTests {
    
    private static Server jetty;

    @BeforeClass
    public static void inicializaAmbiente() throws Exception{
        jetty = new Server();

        Connector connector = new SelectChannelConnector();
        connector.setPort(10000);
        jetty.addConnector(connector);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/prime");
        webAppContext.setWar("target/prime");
        jetty.setHandler(webAppContext);
        jetty.start();
        
    }
    
    @AfterClass
    public static void finalizaAmbiente() throws Exception {
        jetty.stop();
        
    }
    
}
