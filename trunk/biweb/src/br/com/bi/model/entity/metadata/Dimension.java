/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class Dimension extends IdentifiedEntity {

    private List<Level> levels = new ArrayList<Level>();

    /**
     * @return the niveis
     */
    public List<Level> getLevels() {
        return levels;
    }

    /**
     * @param levels the niveis to set
     */
    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
}
