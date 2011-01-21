package br.com.bi.controller.metadata;

import br.com.bi.model.entity.metadata.Property;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

public interface PropertyEdit {

    public Property getProperty();

    public void save();

    public void delete();
}
