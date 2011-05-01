package br.com.proximati.biprime.metadata.entity;

public abstract class Piece {
    private Integer parentIndex = -1;

    public Integer getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(Integer parentIndex) {
        this.parentIndex = parentIndex;
    }
}
