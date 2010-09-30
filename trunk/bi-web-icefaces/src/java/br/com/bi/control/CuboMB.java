/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.control;

import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Cubo;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luiz
 */
public class CuboMB {

    private Cubo cubo;

    /** Creates a new instance of Cubo */
    public CuboMB() {
    }

    public List<Cubo> getCubos() {
        return BiFacade.getInstance().findAllCubos();
    }

    /**
     * @return the cubo
     */
    public Cubo getCubo() {
        return cubo;
    }

    /**
     * @param cubo the cubo to set
     */
    public void setCubo(Cubo cubo) {
        this.cubo = cubo;
    }

    public void alterar() {
        cubo = cubo;
    }
}
