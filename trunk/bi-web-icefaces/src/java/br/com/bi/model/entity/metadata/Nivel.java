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
public class Nivel extends MetadataEntity {

    private String esquema;
    private String tabela;
    private String juncaoNivelSuperior;
    private List<Propriedade> propriedades = new ArrayList<Propriedade>();

    public Nivel() {
    }

    public Nivel(String esquema, String tabela) {
        this.esquema = esquema;
        this.tabela = tabela;
    }

    public Nivel(String esquema, String tabela, String juncaoNivelSuperior) {
        this.esquema = esquema;
        this.tabela = tabela;
        this.juncaoNivelSuperior = juncaoNivelSuperior;
    }

    /**
     * @return the tabela
     */
    public String getTabela() {
        return tabela;
    }

    /**
     * @param tabela the tabela to set
     */
    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    /**
     * @return the propriedades
     */
    public List<Propriedade> getPropriedades() {
        return propriedades;
    }

    /**
     * @param propriedades the propriedades to set
     */
    public void setPropriedades(List<Propriedade> propriedades) {
        this.propriedades = propriedades;
    }

    /**
     * @return the esquema
     */
    public String getEsquema() {
        return esquema;
    }

    /**
     * @param esquema the esquema to set
     */
    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    /**
     * @return the juncaoNivelSuperior
     */
    public String getJuncaoNivelSuperior() {
        return juncaoNivelSuperior;
    }

    /**
     * @param juncaoNivelSuperior the juncaoNivelSuperior to set
     */
    public void setJuncaoNivelSuperior(String juncaoNivelSuperior) {
        this.juncaoNivelSuperior = juncaoNivelSuperior;
    }

    public Propriedade getPropriedadeCodigo() {
        for (Propriedade p : propriedades) {
            if (p.isPropriedadeCodigo()) {
                return p;
            }
        }
        return null;
    }

    public Propriedade getPropriedadeNome() {
        for (Propriedade p : propriedades) {
            if (p.isPropriedadeNome()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void accept(MetadataEntityVisitor visitor) {
        visitor.visit(this);
    }
}
