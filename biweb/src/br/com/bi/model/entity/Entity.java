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
}
