/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity;


/**
 *
 * @author Luiz
 */
public abstract class Entity {
    private int parentIndex = -1;
    
    private boolean persisted;

    /**
     * @return the persisted
     */
    public boolean isPersisted() {
        return persisted;
    }

    /**
     * @param persisted the persisted to set
     */
    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getParentIndex() {
        return parentIndex;
    }
}
