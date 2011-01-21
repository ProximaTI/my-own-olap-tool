package br.com.bi.controller.metadata;

import br.com.bi.model.entity.metadata.Dimension;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

public interface DimensionEdit {

    public Dimension getDimension();

    public void save();

    public void cancel();
}
