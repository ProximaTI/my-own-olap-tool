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

    private String expressao;

    /**
     * @return the expressao
     */
    public String getExpression() {
        return expressao;
    }

    /**
     * @param expressao the expressao to set
     */
    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    @Override
    public void accept(MetadataEntityVisitor visitor) {
        visitor.visit(this);
    }
}
