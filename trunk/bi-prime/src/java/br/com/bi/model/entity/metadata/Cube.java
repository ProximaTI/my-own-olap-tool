package br.com.bi.model.entity.metadata;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "Cube.findAll",
    query = "select o from Cube o")})
@Table(name = "\"cube\"")
public class Cube implements Metadata, Serializable {

    @Column(name = "description")
    private String description;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "schemaName")
    private String schemaName;
    @Column(name = "tableName")
    private String tableName;
    @OneToMany(mappedBy = "cube")
    private List<Filter> filterList;
    @OneToMany(mappedBy = "cube")
    private List<Measure> measureList;
    @OneToMany(mappedBy = "cube")
    private List<CubeLevel> cubeLevelList;

    public Cube() {
    }

    public Cube(String description, Integer id, String name, String schema,
            String table) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.schemaName = schema;
        this.tableName = table;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schema) {
        this.schemaName = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String table) {
        this.tableName = table;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }

    public Filter addFilter(Filter filter) {
        getFilterList().add(filter);
        filter.setCube(this);
        return filter;
    }

    public Filter removeFilter(Filter filter) {
        getFilterList().remove(filter);
        filter.setCube(null);
        return filter;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
    }

    public Measure addMeasure(Measure measure) {
        getMeasureList().add(measure);
        measure.setCube(this);
        return measure;
    }

    public Measure removeMeasure(Measure measure) {
        getMeasureList().remove(measure);
        measure.setCube(null);
        return measure;
    }

    public List<CubeLevel> getCubeLevelList() {
        return cubeLevelList;
    }

    public void setCubeLevelList(List<CubeLevel> cubeLevelList) {
        this.cubeLevelList = cubeLevelList;
    }

    public CubeLevel addCubeLevel(CubeLevel cubeLevel) {
        getCubeLevelList().add(cubeLevel);
        cubeLevel.setCube(this);
        return cubeLevel;
    }

    public CubeLevel removeCubeLevel(CubeLevel cubeLevel) {
        getCubeLevelList().remove(cubeLevel);
        cubeLevel.setCube(null);
        return cubeLevel;
    }

    public Cube clone() {
        Cube clone = new Cube();

        clone.setCubeLevelList(new ArrayList<CubeLevel>(this.getCubeLevelList()));
        clone.setDescription(this.getDescription());
        clone.setFilterList(new ArrayList<Filter>(this.getFilterList()));
        clone.setId(this.getId());
        clone.setMeasureList(new ArrayList<Measure>(this.getMeasureList()));
        clone.setName(this.getName());
        clone.setSchemaName(this.getSchemaName());
        clone.setTableName(this.getTableName());

        return clone;
    }
}
