package br.com.bi.model.entity;

public abstract class Piece {
    private Integer parentIndex;

    public Integer getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }
}
