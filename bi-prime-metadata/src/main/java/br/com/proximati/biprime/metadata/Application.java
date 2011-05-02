/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.metadata;

import br.com.proximati.biprime.metadata.dao.CubeDao;
import br.com.proximati.biprime.metadata.dao.FilterDao;
import br.com.proximati.biprime.metadata.dao.LevelDao;
import br.com.proximati.biprime.metadata.dao.MeasureDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe encapsula o container de injeção do Spring.
 * @author luiz
 */
public class Application {

    static private ApplicationContext context;
    private static final String APPLICATION_CONTEXT_FILE = "META-INF/beans.xml";

    /**
     * @return the context
     */
    public static ApplicationContext getContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_FILE);
        }
        return context;
    }

    public static Object getBean(String beanName) {
        return getContext().getBean(beanName);
    }

    public static CubeDao getCubeDao() {
        return (CubeDao) getContext().getBean("cubeDao");
    }

    public static LevelDao getLevelDao() {
        return (LevelDao) getContext().getBean("levelDao");
    }

    public static FilterDao getFilterDao() {
        return (FilterDao) getContext().getBean("filterDao");
    }

    public static MeasureDao getMeasureDao() {
        return (MeasureDao) getContext().getBean("measureDao");
    }
}
