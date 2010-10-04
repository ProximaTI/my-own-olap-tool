
import br.com.bi.model.BiFacade;
import br.com.bi.model.entity.metadata.Cubo;
import br.com.bi.model.entity.metadata.Dimensao;
import br.com.bi.model.entity.metadata.Metrica;
import br.com.bi.model.entity.metadata.Nivel;
import br.com.bi.model.entity.metadata.Propriedade;
import br.com.bi.model.entity.query.Consulta;
import br.com.bi.model.entity.query.No;

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
        Cubo acessos = new Cubo("stats", "dwf_acesso");

        Metrica quantidade =
                new Metrica(acessos, Metrica.Funcao.SUM, "quantidade");

        Nivel tipoProduto = new Nivel("stats", "dwd_tipo_produto");
        tipoProduto.getPropriedades().add(new Propriedade("idtipoproduto", true, false));
        tipoProduto.getPropriedades().add(new Propriedade("descricao", false, true));

        Nivel produto = new Nivel("stats", "dwd_tipo_produto", "idtipoproduto");
        produto.getPropriedades().add(new Propriedade("idproduto", true, false));
        produto.getPropriedades().add(new Propriedade("nomeproduto", false, true));

        Nivel categoria = new Nivel("stats", "dwd_categoria");
        categoria.getPropriedades().add(new Propriedade("idcategoria", true, false));
        categoria.getPropriedades().add(new Propriedade("descricao", false, true));

        Dimensao categoriaDimensao = new Dimensao();
        categoriaDimensao.getNiveis().add(categoria);

        Dimensao produtoDimensao = new Dimensao();
        produtoDimensao.getNiveis().add(tipoProduto);
        produtoDimensao.getNiveis().add(produto);

        // coluna
        Consulta consulta = new Consulta(acessos);
        consulta.getColuna().getFilhos().add(new No(consulta.getColuna(), quantidade));

        // linha
        No noProduto = new No(consulta.getLinha(), tipoProduto);
        noProduto.getFilhos().add(new No(noProduto, produto));

        No noCategoria = new No(consulta.getLinha(), categoria);

        consulta.getLinha().getFilhos().add(noProduto);
        consulta.getLinha().getFilhos().add(noCategoria);

        System.out.println(BiFacade.getInstance().traduzirParaSql(consulta));
    }
}
