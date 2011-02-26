/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe encapsula o container de injeção do Spring.
 * @author luiz
 */
public class Application {

    static private ApplicationContext context;

    /**
     * @return the context
     */
    public static ApplicationContext getContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("/beans.xml");
        }
        return context;
    }
}
