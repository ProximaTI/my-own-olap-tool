
import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.Dimension;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Property;
import br.com.bi.model.entity.query.Query;
import br.com.bi.model.entity.query.Node;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luiz
 */
public class Main {

    public static void main(String args[]) {
        Cube acessos = new Cube("stats", "dwf_acesso");

        Measure quantidade =
                new Measure(acessos, Measure.Funcao.SUM, "quantidade");

        Level tipoProduto = new Level("stats", "dwd_tipo_produto");
        tipoProduto.getPropriedades().add(new Property("idtipoproduto", true, false));
        tipoProduto.getPropriedades().add(new Property("descricao", false, true));

        Level produto = new Level("stats", "dwd_tipo_produto", "idtipoproduto");
        produto.getPropriedades().add(new Property("idproduto", true, false));
        produto.getPropriedades().add(new Property("nomeproduto", false, true));

        Level categoria = new Level("stats", "dwd_categoria");
        categoria.getPropriedades().add(new Property("idcategoria", true, false));
        categoria.getPropriedades().add(new Property("descricao", false, true));

        Dimension categoriaDimensao = new Dimension();
        categoriaDimensao.getNiveis().add(categoria);

        Dimension produtoDimensao = new Dimension();
        produtoDimensao.getNiveis().add(tipoProduto);
        produtoDimensao.getNiveis().add(produto);

        // coluna
        Query consulta = new Query(acessos);
        consulta.getColumns().getChildren().add(new Node(consulta.getColumns(), quantidade));

        // linha
        Node noProduto = new Node(consulta.getRows(), tipoProduto);
        noProduto.getChildren().add(new Node(noProduto, produto));

        Node noCategoria = new Node(consulta.getRows(), categoria);

        consulta.getRows().getChildren().add(noProduto);
        consulta.getRows().getChildren().add(noCategoria);

        System.out.println(BiFacade.getInstance().traduzirParaSql(consulta));
    }
}
