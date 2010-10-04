/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.control;

import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Cube;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luiz
 */
public class CuboMB {

    private Cube cubo;

    /** Creates a new instance of Cubo */
    public CuboMB() {
    }

    public List<Cube> getCubos() {
        return BiFacade.getInstance().findAllCubos();
    }

    /**
     * @return the cubo
     */
    public Cube getCubo() {
        return cubo;
    }

    /**
     * @param cubo the cubo to set
     */
    public void setCubo(Cube cubo) {
        this.cubo = cubo;
    }

    public void alterar() {
        cubo = cubo;
    }
}
