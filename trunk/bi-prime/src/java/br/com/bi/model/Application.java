/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model;

import br.com.bi.model.dao.CubeDao;
import br.com.bi.model.dao.LevelDao;
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

    public static CubeDao getCubeDao() {
        return (CubeDao) getContext().getBean("cubeDao");
    }
    
    public static LevelDao getLevelDao() {
        return (LevelDao) getContext().getBean("levelDao");
    }
}
