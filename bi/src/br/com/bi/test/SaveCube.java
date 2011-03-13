/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.test;

import br.com.bi.model.MetadataFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.CubeLevel;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.model.entity.metadata.Property;

/**
 *
 * @author Luiz
 */
public class SaveCube {

    public static void main(String args[]) {
        Property codigo = new Property("idcategoria", true, false);
        codigo.setName("Código");

        Level categoria = new Level();
        categoria.setSchema("stats");
        categoria.setTable("dwd_categoria");
        categoria.setIndex(0);
        categoria.setName("Categoria");
        categoria.getProperties().add(codigo);

        Dimension categoriaDimensao = new Dimension();
        categoriaDimensao.setName("Categoria");
        categoriaDimensao.getLevels().add(categoria);

        MetadataFacade.getInstance().save(categoriaDimensao);

        Measure quantidade = new Measure(Measure.Funcao.SUM, "quantidade");
        quantidade.setName("Total de acessos");

        CubeLevel categoriaCubo = new CubeLevel();
        categoriaCubo.setJoinColumn("idcategoria");
        categoriaCubo.setLevel(categoria);

        Cube cube = new Cube("stats", "dwf_acesso");
        cube.getMeasures().add(quantidade);
        cube.getCubeLevels().add(categoriaCubo);

        MetadataFacade.getInstance().save(cube);
    }
}