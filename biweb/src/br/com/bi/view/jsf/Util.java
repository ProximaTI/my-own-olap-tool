package br.com.bi.view.jsf;


import java.util.PropertyResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


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

    public static void showPopup(String clientId) {
        FacesContext context = FacesContext.getCurrentInstance();
        String popupId = clientId;
        StringBuilder script = new StringBuilder();
        script.append("AdfPage.PAGE.findComponent('").append(popupId).append("').show();");
        ExtendedRenderKitService erks =

            Service.getService(context.getRenderKit(),
                               ExtendedRenderKitService.class);

        erks.addScript(context, script.toString());
    }

    public static void closePopup(String popupId) {
        StringBuilder script = new StringBuilder();
        FacesContext context = FacesContext.getCurrentInstance();
        script.append("AdfPage.PAGE.findComponent('").append(popupId).append("').hide();");

        ExtendedRenderKitService erks =
            Service.getService(context.getRenderKit(),
                               ExtendedRenderKitService.class);
        erks.addScript(context, script.toString());
    }
}
