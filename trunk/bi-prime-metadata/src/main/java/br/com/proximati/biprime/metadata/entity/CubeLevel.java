package br.com.proximati.biprime.metadata.entity;



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
@NamedQueries( { @NamedQuery(name = "CubeLevel.findAll",
                             query = "select o from CubeLevel o") })
@Table(name = "\"cube_level\"")
public class CubeLevel extends Piece implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "joinColumn")
    private String joinColumn;
    @ManyToOne
    @JoinColumn(name = "levelId")
    private Level level;
    @ManyToOne
    @JoinColumn(name = "cubeId")
    private Cube cube;

    public CubeLevel() {
    }

    public CubeLevel(Cube cube, Integer id, String joinColumn, Level level) {
        this.cube = cube;
        this.id = id;
        this.joinColumn = joinColumn;
        this.level = level;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJoinColumn() {
        return joinColumn;
    }

    public void setJoinColumn(String joinColumn) {
        this.joinColumn = joinColumn;
    }


    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }
    
      public CubeLevel clone() {
        CubeLevel clone = new CubeLevel();

        clone.setCube(this.getCube());
        clone.setId(this.getId());
        clone.setJoinColumn(this.getJoinColumn());
        clone.setLevel(this.getLevel());
        clone.setParentIndex(this.getParentIndex());

        return clone;
    }
}
