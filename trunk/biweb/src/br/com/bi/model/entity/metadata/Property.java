/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

/**
 *
 * @author Luiz
 */
public class Property extends MetadataEntity {

    private String coluna;
    private boolean propriedadeCodigo;
    private boolean propriedadeNome;

    public Property() {
    }

    public Property(String coluna, boolean propriedadeCodigo,
            boolean propriedadeNome) {
        this.coluna = coluna;
        this.propriedadeCodigo = propriedadeCodigo;
        this.propriedadeNome = propriedadeNome;
    }

    /**
     * @return the coluna
     */
    public String getColumn() {
        return coluna;
    }

    /**
     * @param coluna the coluna to set
     */
    public void setColuna(String coluna) {
        this.coluna = coluna;
    }

    /**
     * @return the propriedadeCodigo
     */
    public boolean isCodeProperty() {
        return propriedadeCodigo;
    }

    /**
     * @param propriedadeCodigo the propriedadeCodigo to set
     */
    public void setPropriedadeCodigo(boolean propriedadeCodigo) {
        this.propriedadeCodigo = propriedadeCodigo;
    }

    /**
     * @return the propriedadeNome
     */
    public boolean isNameProperty() {
        return propriedadeNome;
    }

    /**
     * @param propriedadeNome the propriedadeNome to set
     */
    public void setPropriedadeNome(boolean propriedadeNome) {
        this.propriedadeNome = propriedadeNome;
    }

    @Override
    public void accept(MetadataEntityVisitor visitor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
