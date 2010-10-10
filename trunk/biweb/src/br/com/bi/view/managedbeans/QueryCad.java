package br.com.bi.view.managedbeans;


import br.com.bi.model.QueryFacade;
import br.com.bi.model.entity.Entity;
import br.com.bi.model.entity.queries.Query;
import br.com.bi.view.managedbeans.util.AbstractCadBean;
import br.com.bi.view.managedbeans.util.EditingListener;

import java.util.List;

import oracle.adf.view.rich.component.rich.data.RichTable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Backing bean para o cadastro de consultas.
 *
 * @author Luiz Augusto Garcia da Silva
 */
 @Component
 @Scope("session")
public class QueryCad extends AbstractCadBean implements EditingListener {
    public static final String QUERY_CAD_ACTION = "queryCad";

    private RichTable table;

    /**
     * @param table
     */
    public void setTable(RichTable table) {
        this.table = table;
    }

    /**
     * @return
     */
    public RichTable getTable() {
        return table;
    }

    /**
     * @return
     */
    public List<Query> getQueries() {
        return QueryFacade.getInstance().getQueries();
    }

    /**
     * Responde pela exclusão de um objeto.
     * @return
     */
    public String delete() {
        QueryFacade.getInstance().deleteQuery((Query)table.getSelectedRowData());
        return null;
    }

    /**
     * Retorna o objeto selecionado na tabela do cadastro.
     * @return
     */
    public Entity getSelectedEntity() {
        return (Entity)table.getSelectedRowData();
    }

    /**
     * Notificação da persistência de um objeto que foi salvo na tela de edição.
     * @param entity
     * @return
     */
    public String entitySaved(Entity entity) {
        return QUERY_CAD_ACTION;
    }

    /**
     * Notificação do cancelamento da edição.
     * @return
     */
    public String editingCanceled() {
        return QUERY_CAD_ACTION;
    }
}
