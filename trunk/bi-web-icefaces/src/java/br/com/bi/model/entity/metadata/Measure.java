/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

/**
 *
 * @author Luiz
 */
public class Measure extends MetadataEntity {

    private Cube cubo;
    private Funcao funcao;
    private String coluna;
    private String expressaoFiltro;
    private boolean metricaPadrao;

    public Measure() {
    }

    public Measure(Cube cubo, Funcao funcao, String coluna) {
        this.cubo = cubo;
        this.funcao = funcao;
        this.coluna = coluna;
    }

    /**
     * @return the funcao
     */
    public Funcao getFuncao() {
        return funcao;
    }

    /**
     * @param funcao the funcao to set
     */
    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
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
     * @return the expressaoFiltro
     */
    public String getFilterExpression() {
        return expressaoFiltro;
    }

    /**
     * @param expressaoFiltro the expressaoFiltro to set
     */
    public void setExpressaoFiltro(String expressaoFiltro) {
        this.expressaoFiltro = expressaoFiltro;
    }

    /**
     * @return the metricaPadrao
     */
    public boolean isMetricaPadrao() {
        return metricaPadrao;
    }

    /**
     * @param metricaPadrao the metricaPadrao to set
     */
    public void setMetricaPadrao(boolean metricaPadrao) {
        this.metricaPadrao = metricaPadrao;
    }

    public void setCodigoFuncao(int funcao) {
        setFuncao(codigoToFuncao(funcao));
    }

    public int getCodigoFuncao() {
        if (funcao == Funcao.SUM) {
            return 1;
        } else if (funcao == Funcao.COUNT) {
            return 2;
        } else {
            return 3;
        }
    }

    private Funcao codigoToFuncao(int funcao) {
        switch (funcao) {
            case 1:
                return Funcao.SUM;
            case 2:
                return Funcao.COUNT;
            case 3:
                return Funcao.AVG;
            default:
                return null;
        }
    }

    /**
     * @return the cubo
     */
    public Cube getCube() {
        return cubo;
    }

    /**
     * @param cubo the cubo to set
     */
    public void setCubo(Cube cubo) {
        this.cubo = cubo;
    }

    @Override
    public void accept(MetadataEntityVisitor visitor) {
        visitor.visit(this);
    }

    public enum Funcao {

        SUM, COUNT, AVG
    }
}
