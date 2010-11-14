package br.com.bi.model.entity.metadata;

import br.com.bi.model.entity.Piece;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "Measure.findAll", query = "select o from Measure o")
})
@Table(name = "\"measure\"")
public class Measure extends Piece implements Serializable {
    @Column(name="aggregateFunction")
    private Integer aggregateFunction;
    @Column(name="columnName")
    private String columnName;
    @Column(name="defaultMeasure")
    private String defaultMeasure;
    @Column(name="description")
    private String description;
    @Column(name="filterExpression")
    private String filterExpression;
    @Id
    @Column(name="id", nullable = false)
    private Integer id;
    @Column(name="name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "cubeId")
    private Cube cube;

    public Measure() {
    }

    public Measure(Integer aggregateFunction, String columnName, Cube cube, String defaultMeasure, String description,
                   String filterExpression, Integer id, String name) {
        this.aggregateFunction = aggregateFunction;
        this.columnName = columnName;
        this.cube = cube;
        this.defaultMeasure = defaultMeasure;
        this.description = description;
        this.filterExpression = filterExpression;
        this.id = id;
        this.name = name;
    }

    public Integer getAggregateFunction() {
        return aggregateFunction;
    }

    public void setAggregateFunction(Integer aggregateFunction) {
        this.aggregateFunction = aggregateFunction;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


    public String getDefaultMeasure() {
        return defaultMeasure;
    }

    public void setDefaultMeasure(String defaultMeasure) {
        this.defaultMeasure = defaultMeasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilterExpression() {
        return filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
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

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }
}
