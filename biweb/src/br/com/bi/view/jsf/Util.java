package br.com.bi.view.jsf;


import java.util.PropertyResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;


public class Util {
    private static ValueExpression createExpression(String varName,
                                                    Class clazz) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expf = ctx.getApplication().getExpressionFactory();
        ValueExpression ve =
            expf.createValueExpression(ctx.getELContext(), "#{" + varName +
                                       "}", clazz);
        return ve;
    }

    public static Object getELVar(String varName) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ELContext el = ctx.getELContext();
        ValueExpression ve = createExpression(varName, Object.class);
        return ve.getValue(el);
    }

    public static String getBundleValue(String key) {
        PropertyResourceBundle prb =
            (PropertyResourceBundle)getELVar("biwebBundle");
        return prb.getString(key);
    }
}
