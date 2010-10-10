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
import br.com.bi.model.entity.query.Node;
import br.com.bi.model.entity.query.Query;

/**
 *
 * @author Luiz
 */
public class SaveQuery {

    public static void main(String args[]) {
        Property idcategoria = new Property("idcategoria", true, false);
        idcategoria.setName("Código");

        Level categoria = new Level();
        categoria.setSchema("stats");
        categoria.setTable("dwd_categoria");
        categoria.setIndex(0);
        categoria.setName("Categoria");
        categoria.getProperties().add(idcategoria);

        Dimension d_categoria = new Dimension();
        d_categoria.setName("Categoria");
        d_categoria.getLevels().add(categoria);

        MetadataFacade.getInstance().save(d_categoria);

        CubeLevel c_categoria = new CubeLevel();
        c_categoria.setJoinColumn("idcategoria");
        c_categoria.setLevel(categoria);

        // ================================================

        Property idtipoproduto = new Property("idtipoproduto", true, false);
        idtipoproduto.setName("Código");

        Level tipoProduto = new Level();
        tipoProduto.setSchema("stats");
        tipoProduto.setTable("dwd_tipo_produto");
        tipoProduto.setIndex(0);
        tipoProduto.setName("Tipo Produto");
        tipoProduto.getProperties().add(idtipoproduto);

        Property idproduto = new Property("idproduto", true, false);
        idproduto.setName("Código");

        Level produto = new Level();
        produto.setSchema("stats");
        produto.setTable("dwd_produto_eletronico");
        produto.setIndex(1);
        produto.setName("Produto");
        produto.setJoinColumnUpperLevel("idtipoproduto");
        produto.getProperties().add(idproduto);

        Dimension d_produto = new Dimension();
        d_produto.setName("Produto");
        d_produto.getLevels().add(tipoProduto);
        d_produto.getLevels().add(produto);

        MetadataFacade.getInstance().save(d_produto);

        Measure quantidade = new Measure(Measure.Funcao.SUM, "quantidade");
        quantidade.setName("Total de acessos");

        CubeLevel c_produto = new CubeLevel();
        c_produto.setJoinColumn("idproduto");
        c_produto.setLevel(produto);

        Cube acessos = new Cube("stats", "dwf_acesso");
        acessos.getMeasures().add(quantidade);
        acessos.getCubeLevels().add(c_produto);
        acessos.getCubeLevels().add(c_categoria);


        MetadataFacade.getInstance().save(acessos);

        // consulta

        Node n_tipoProduto = new Node();
        n_tipoProduto.setMetadataEntity(tipoProduto);
        n_tipoProduto.addChildren(new Node(produto));

        Node n_categoria = new Node();
        n_categoria.setMetadataEntity(categoria);

        Node n_quantidade = new Node();
        n_quantidade.setMetadataEntity(quantidade);

        Query query = new Query(acessos);
        query.getRows().addChildren(n_tipoProduto);
        query.getRows().addChildren(n_categoria);
        query.getColumns().addChildren(n_quantidade);

        System.out.println(MetadataFacade.getInstance().translateToSql(query));
    }
}
