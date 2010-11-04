/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

/**
 *
 * @author Luiz
 */
public class Filter extends IdentifiedEntity {

    private String expression;

    /**
     * @return the expressao
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression the expressao to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Filter clone() {
        Filter clone = new Filter();
        clone.setDescription(this.getDescription());
        clone.setExpression(this.expression);
        clone.setId(this.getId());
        clone.setName(this.getName());
        clone.setParentIndex(this.getParentIndex());
        clone.setPersisted(this.isPersisted());

        return clone;
    }
}
