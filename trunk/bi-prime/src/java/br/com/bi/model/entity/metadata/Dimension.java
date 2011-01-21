package br.com.bi.model.entity.metadata;

import java.io.Serializable;

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
  @NamedQuery(name = "Dimension.findAll", query = "select o from Dimension o")
})
@Table(name = "\"dimension\"")
public class Dimension implements Serializable {
    @Column(name="description")
    private String description;
    @Id
    @Column(name="id", nullable = false)
    private Integer id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "dimension")
    private List<Level> levelList;

    public Dimension() {
    }

    public Dimension(String description, Integer id, String name) {
        this.description = description;
        this.id = id;
        this.name = name;
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

    public List<Level> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Level> levelList) {
        this.levelList = levelList;
    }

    public Level addLevel(Level level) {
        getLevelList().add(level);
        level.setDimension(this);
        return level;
    }

    public Level removeLevel(Level level) {
        getLevelList().remove(level);
        level.setDimension(null);
        return level;
    }
}
