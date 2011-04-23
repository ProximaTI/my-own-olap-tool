package br.com.bi.ui.util;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("genericConverter")
@Scope("prototype")
public class GenericConverter implements Converter {

    private List<Object> list;

    public GenericConverter() {
    }

    public GenericConverter(List<Object> list) {
        this.list = list;
    }

    public GenericConverter create(List<Object> list) {
        return new GenericConverter(list);
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent component, String key) {
        if (list == null) {
            return null;
        }
        return list.get(Integer.parseInt(key));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent component, Object obj) {
        return new Integer(list.indexOf(obj)).toString();
    }
}
