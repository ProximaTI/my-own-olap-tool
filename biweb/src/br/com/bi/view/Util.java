package br.com.bi.view;


import javax.el.ELContext;

import javax.faces.context.FacesContext;


public class Util {
    
    public static Object getManagedBean(String beanName) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        return FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext,
                                                                                           null,
                                                                                           beanName);
    }
}
