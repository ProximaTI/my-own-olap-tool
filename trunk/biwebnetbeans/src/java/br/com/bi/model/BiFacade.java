/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model;

import br.com.bi.model.dao.CuboDao;
import br.com.bi.model.entity.metadata.Cubo;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class BiFacade {

    private static BiFacade instance;

    private BiFacade() {
    }

    public static BiFacade getInstance() {
        if (instance == null) {
            instance = new BiFacade();
        }
        return instance;
    }

    public void salvarCubo(Cubo cubo) {
        ((CuboDao) Application.getContext().getBean("cuboDAO")).salvar(cubo);
    }

    public Cubo findCuboById(int id) {
        return ((CuboDao) Application.getContext().getBean("cuboDAO")).
                findCuboById(id);
    }

    public List<Cubo> findAllCubos() {
        return ((CuboDao) Application.getContext().getBean("cuboDAO")).
                findAllCubos();
    }

    public void apagarCubo(int id) {
        ((CuboDao) Application.getContext().getBean("cuboDAO")).apagar(id);
    }
}
