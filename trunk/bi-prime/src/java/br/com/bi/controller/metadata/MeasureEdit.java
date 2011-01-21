package br.com.bi.controller.metadata;

import br.com.bi.model.entity.metadata.Measure;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

public interface MeasureEdit {

    public Measure getMeasure();

    public void save();

    public void delete();
}
