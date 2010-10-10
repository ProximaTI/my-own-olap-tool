/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

/**
 *
 * @author Luiz
 */
public class Filter extends MetadataEntity {

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
}
