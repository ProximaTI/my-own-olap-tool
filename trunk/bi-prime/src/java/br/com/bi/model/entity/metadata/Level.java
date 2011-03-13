package br.com.bi.model.entity.metadata;

import br.com.bi.model.entity.Piece;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "Level.findAll",
    query = "select o from Level o")})
@Table(name = "\"level\"")
public class Level extends Piece implements Serializable {

    @Column(name = "description")
    private String description;
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "indice")
    private Integer indice;
    @Column(name = "name")
    private String name;
    @Column(name = "schemaName")
    private String schemaName;
    @Column(name = "tableName")
    private String tableName;
    @Column(name = "upperLevelJoinColumn")
    private String upperLevelJoinColumn;
    @ManyToOne
    @JoinColumn(name = "dimensionId")
    private Dimension dimension;
    @OneToMany(mappedBy = "level")
    private List<Property> propertyList;
    @OneToMany(mappedBy = "level")
    private List<CubeLevel> cubeLevelList;

    public Level() {
    }

    public Level(String description, Dimension dimension, Integer id,
            Integer indice, String name, String schemaName,
            String tableName, String upperLevelJoinColumn) {
        this.description = description;
        this.dimension = dimension;
        this.id = id;
        this.indice = indice;
        this.name = name;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.upperLevelJoinColumn = upperLevelJoinColumn;
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

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
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

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUpperLevelJoinColumn() {
        return upperLevelJoinColumn;
    }

    public void setUpperLevelJoinColumn(String upperLevelJoinColumn) {
        this.upperLevelJoinColumn = upperLevelJoinColumn;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public Property addProperty(Property property) {
        getPropertyList().add(property);
        property.setLevel(this);
        return property;
    }

    public Property removeProperty(Property property) {
        getPropertyList().remove(property);
        property.setLevel(null);
        return property;
    }

    public List<CubeLevel> getCubeLevelList() {
        return cubeLevelList;
    }

    public void setCubeLevelList(List<CubeLevel> cubeLevelList) {
        this.cubeLevelList = cubeLevelList;
    }

    public CubeLevel addCubeLevel(CubeLevel cubeLevel) {
        getCubeLevelList().add(cubeLevel);
        cubeLevel.setLevel(this);
        return cubeLevel;
    }

    public CubeLevel removeCubeLevel(CubeLevel cubeLevel) {
        getCubeLevelList().remove(cubeLevel);
        cubeLevel.setLevel(null);
        return cubeLevel;
    }

    public Property getCodeProperty() {
        for (Property p : propertyList) {
            if (p.getCodeProperty()) {
                return p;
            }
        }
        return null;
    }

    public Property getNameProperty() {
        for (Property p : propertyList) {
            if (p.getNameProperty()) {
                return p;
            }
        }
        return null;
    }

    public List<Level> getLowerLevels() {
        List<Level> levels = this.getDimension().getLevelList();

        return levels.subList(levels.indexOf(this), levels.size() - 1);
    }
}
