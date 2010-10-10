package br.com.bi.control.metadata;

import br.com.bi.model.entity.metadata.Level;

public interface LevelEdit {
    public Level getLevel();

    public void save();

    public void delete();
}
