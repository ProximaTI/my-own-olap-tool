package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Filter;

public interface FilterEdit {
    public Filter getFilter();

    public void save();

    public void cancel();
}
