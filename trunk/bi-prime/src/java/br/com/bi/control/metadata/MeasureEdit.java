package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Measure;

public interface MeasureEdit {
    public Measure getMeasure();

    public void save();

    public void delete();
}
