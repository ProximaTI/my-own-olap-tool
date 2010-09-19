package br.com.bi.view.managedbeans.util;


import java.util.ResourceBundle;

import javax.el.ELContext;

import javax.faces.context.FacesContext;


public abstract class AbstractManagedBean {

    /**
     * @param key
     * @return
     */
    public String getValueFromBundleByKey(String key) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ResourceBundle bundle =
            (ResourceBundle)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext,
                                                                                                        null,
                                                                                                        "biwebBundle");
        return bundle.getString(key);
    }
}
