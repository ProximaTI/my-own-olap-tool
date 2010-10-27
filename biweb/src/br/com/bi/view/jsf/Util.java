package br.com.bi.view.jsf;


import java.util.PropertyResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;


/**
 * Classe com métodos utilitários auxiliar no provimento de serviços relacionados
 * ao framework JSF.
 *
 * @author Luiz Augusto
 */
public class Util {
    public static final String BIWEB_BUNDLE = "biwebBundle";

    private static ValueExpression createExpression(String varName,
                                                    Class clazz) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expf = ctx.getApplication().getExpressionFactory();

        StringBuilder sb = new StringBuilder();
        sb.append("#{").append(varName).append("}");
        ValueExpression ve =
            expf.createValueExpression(ctx.getELContext(), sb.toString(),
                                       clazz);
        return ve;
    }

    /**
     * Retorna o valor de uma expressão.
     * @param varName
     * @return
     */
    public static Object getELVar(String varName) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ELContext el = ctx.getELContext();
        ValueExpression ve = createExpression(varName, Object.class);
        return ve.getValue(el);
    }

    /**
     * Retorna o valor traduzido do bundle dada sua chave.
     * @param key
     * @return
     */
    public static String getBundleValue(String key) {
        PropertyResourceBundle prb =
            (PropertyResourceBundle)getELVar(BIWEB_BUNDLE);
        return prb.getString(key);
    }
}
