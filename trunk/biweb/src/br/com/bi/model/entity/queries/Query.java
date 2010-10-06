package br.com.bi.model.entity.queries;


import br.com.bi.model.entity.Entity;

/**
 * Classe que representa uma consulta multidimensional.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class Query extends Entity {

    private Integer id;
    private String name;
    private String cubeName;
    private Node rowsAxis;
    private Node columnsAxis;
    private boolean rowsAxisNonEmpty = true;
    private boolean columnsAxisNonEmpty = true;

    public Query() {
    }

    public Query(String cubeName) {
        this.cubeName = cubeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getRowsAxis() {
        return rowsAxis;
    }

    public void setRowsAxis(Node rowsAxis) {
        this.rowsAxis = rowsAxis;
    }

    public Node getColumnsAxis() {
        return columnsAxis;
    }

    public void setColumnsAxis(Node columnsAxis) {
        this.columnsAxis = columnsAxis;
    }

    public boolean isRowsAxisNonEmpty() {
        return rowsAxisNonEmpty;
    }

    public void setRowsAxisNonEmpty(boolean rowsAxisNonEmpty) {
        this.rowsAxisNonEmpty = rowsAxisNonEmpty;
    }

    public boolean isColumnsAxisNonEmpty() {
        return columnsAxisNonEmpty;
    }

    public void setColumnsAxisNonEmpty(boolean columnsAxisNonEmpty) {
        this.columnsAxisNonEmpty = columnsAxisNonEmpty;
    }

    public String getCubeName() {
        return cubeName;
    }

    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }

    @Override
    public Query clone() {
        Query clone = new Query();

        clone.setCubeName(getCubeName());
        clone.setId(getId());
        clone.setName(getName());
        clone.setRowsAxis(getRowsAxis().clone());
        clone.setColumnsAxis(getColumnsAxis().clone());
        clone.setRowsAxisNonEmpty(isRowsAxisNonEmpty());
        clone.setColumnsAxisNonEmpty(isColumnsAxisNonEmpty());
        clone.setPersisted(isPersisted());

        return clone;
    }
}
