/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author carlos
 */
public class AllIntegrationTests {

    WebDriver driver;
    String pagina = "";
    
    @Before
    public void iniciaEstoria(){
        driver = new FirefoxDriver();
    }

    @After
    public void fimEstoria(){
        driver.close();
    }
    
    @Test
    public void acessarPagina(){
        driver.get("http://localhost:10000/prime/teste.jsf");
        pagina = driver.getPageSource();

        System.out.println("p2"+pagina);
        Assert.assertTrue(pagina.contains("Hello"));
    }
    
}
