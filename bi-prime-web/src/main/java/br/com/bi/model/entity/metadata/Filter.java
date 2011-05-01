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
    @NamedQuery(name = "Filter.findAll",
    query = "select o from Filter o")})
@Table(name = "\"filter\"")
public class Filter extends Piece implements Metadata, Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "description")
    private String description;
    @Column(name = "expression")
    private String expression;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "cubeId")
    private Cube cube;

    public Filter() {
    }

    public Filter(Cube cube, String description, String expression, Integer id,
            String name) {
        this.cube = cube;
        this.description = description;
        this.expression = expression;
        this.id = id;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
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

    @Override
    public Filter clone() {
        Filter clone = new Filter();
        clone.setCube(this.getCube());
        clone.setDescription(this.getDescription());
        clone.setExpression(this.getExpression());
        clone.setId(this.getId());
        clone.setName(this.getName());
        clone.setParentIndex(this.getParentIndex());

        return clone;
    }
}
