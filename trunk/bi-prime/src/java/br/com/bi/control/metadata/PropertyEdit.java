package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Property;

public interface PropertyEdit {
    public Property getProperty();

    public void save();

    public void delete();
}
