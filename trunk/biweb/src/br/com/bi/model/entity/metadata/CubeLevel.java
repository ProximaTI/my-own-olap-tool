/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;


import br.com.bi.model.entity.Entity;

/**
 *
 * @author Luiz
 */
public class CubeLevel extends Entity {

    private Level level;
    private String joinColumn;

    /**
     * @return the nivel
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @param level the nivel to set
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @return the colunaJuncao
     */
    public String getJoinColumn() {
        return joinColumn;
    }

    /**
     * @param joinColumn the colunaJuncao to set
     */
    public void setJoinColumn(String joinColumn) {
        this.joinColumn = joinColumn;
    }

    /**
     * @return
     */
    public CubeLevel clone() {
        CubeLevel clone = new CubeLevel();
        clone.setLevel(this.level);
        clone.setJoinColumn(this.joinColumn);
        clone.setPersisted(this.isPersisted());
        clone.setParentIndex(this.getParentIndex());
        
        return clone;
    }
}
