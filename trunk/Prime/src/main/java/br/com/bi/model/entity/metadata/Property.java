package br.com.bi.model.entity.metadata;

import br.com.bi.model.entity.Piece;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "Property.findAll", query = "select o from Property o")
})
@Table(name = "\"property\"")
public class Property extends Piece implements Metadata, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "codeProperty")
    private Boolean codeProperty;
    @Column(name = "columnName")
    private String columnName;
    @Column(name = "description")
    private String description;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "nameProperty")
    private Boolean nameProperty;
    @ManyToOne
    @JoinColumn(name = "levelId")
    private Level level;

    public Property() {
    }

    public Property(Boolean codeProperty, String columnName, String description,
            Integer id, Level level, String name,
            Boolean nameProperty) {
        this.codeProperty = codeProperty;
        this.columnName = columnName;
        this.description = description;
        this.id = id;
        this.level = level;
        this.name = name;
        this.nameProperty = nameProperty;
    }

    public Boolean getCodeProperty() {
        return codeProperty;
    }

    public void setCodeProperty(Boolean codeProperty) {
        this.codeProperty = codeProperty;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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

    public Boolean getNameProperty() {
        return nameProperty;
    }

    public void setNameProperty(Boolean nameProperty) {
        this.nameProperty = nameProperty;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
