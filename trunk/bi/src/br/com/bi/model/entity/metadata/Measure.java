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

    private Funcao function;
    private String column;
    private String filterExpression;
    private boolean defaultMeasure;

    public Measure() {
    }

    public Measure(Funcao function, String coluna) {
        this.function = function;
        this.column = coluna;
    }

    /**
     * @return the funcao
     */
    public Funcao getFunction() {
        return function;
    }

    /**
     * @param function the funcao to set
     */
    public void setFunction(Funcao function) {
        this.function = function;
    }

    /**
     * @return the coluna
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the coluna to set
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return the expressaoFiltro
     */
    public String getFilterExpression() {
        return filterExpression;
    }

    /**
     * @param filterExpression the expressaoFiltro to set
     */
    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    /**
     * @return the metricaPadrao
     */
    public boolean isDefaultMeasure() {
        return defaultMeasure;
    }

    /**
     * @param defaultMeasure the metricaPadrao to set
     */
    public void setDefaultMeasure(boolean defaultMeasure) {
        this.defaultMeasure = defaultMeasure;
    }

    public void setCodigoFuncao(int funcao) {
        setFunction(codigoToFuncao(funcao));
    }

    public int getCodigoFuncao() {
        if (function == Funcao.SUM) {
            return 1;
        } else if (function == Funcao.COUNT) {
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

    public enum Funcao {
        SUM, COUNT, AVG
    }
}
