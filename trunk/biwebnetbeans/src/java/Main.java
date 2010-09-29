
import br.com.bi.model.entity.metadata.Cubo;
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

        Metrica quantidade = new Metrica(Metrica.Funcao.SUM, "quantidade");

        Nivel tipoProduto = new Nivel("stats", "dwd_tipo_produto");
        tipoProduto.getPropriedades().add(new Propriedade("idtipoproduto", true, false));
        tipoProduto.getPropriedades().add(new Propriedade("descricao", false, true));

        Nivel produto = new Nivel("stats", "dwd_tipo_produto", "idtipoproduto");
        produto.getPropriedades().add(new Propriedade("idproduto", true, false));
        produto.getPropriedades().add(new Propriedade("nomeproduto", false, true));

        Nivel categoria = new Nivel("stats", "dwd_categoria");
        categoria.getPropriedades().add(new Propriedade("idcategoria", true, false));
        categoria.getPropriedades().add(new Propriedade("descricao", false, true));

        Consulta consulta = new Consulta(acessos);
        consulta.getColuna().getFilhos().add(new No(quantidade));

        No noProduto = new No(tipoProduto);
        noProduto.getFilhos().add(new No(produto));

        consulta.getLinha().getFilhos().add(noProduto);
        consulta.getLinha().getFilhos().add(new No(categoria));

        System.out.println(toSql(consulta));
    }

    private static String toSql(Consulta consulta) {
        String sql = "SELECT ";
        sql += toSql(consulta.getLinha());
        sql += toSql(consulta.getColuna());

        sql += " FROM ";
        sql += consulta.getCubo().getEsquema() + "." + consulta.getCubo().
                getTabela();

        return sql;
    }

    private static String toSql(No no) {
        String sql = new String();
        
        if (no.getEntity() != null) {
            if (no.getEntity() instanceof Nivel) {
                Nivel nivel = (Nivel) no.getEntity();
                sql = nivel.getTabela() + "." + nivel.getPropriedadeCodigo().
                        getColuna() + " ";
            } else if (no.getEntity() instanceof Metrica) {
                Metrica metrica = (Metrica) no.getEntity();
                // TODO colocar o nome da tabela
                sql = metrica.getFuncao() + "(" + metrica.getColuna() + ")";
            }
        }

        for (No filho : no.getFilhos()) {
            sql += " " + toSql(filho);
        }
        return sql;
    }
}
