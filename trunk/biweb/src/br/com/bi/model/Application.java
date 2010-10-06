/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    static private ApplicationContext context =
            new ClassPathXmlApplicationContext("/beans.xml");

    /**
     * @return the context
     */
    public static ApplicationContext getContext() {
        return context;
    }
}
