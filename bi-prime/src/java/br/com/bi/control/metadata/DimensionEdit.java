package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Dimension;

public interface DimensionEdit {
    public Dimension getDimension();

    public void save();

    public void cancel();
}
