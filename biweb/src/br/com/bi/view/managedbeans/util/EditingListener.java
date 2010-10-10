package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;

/**
 * Interface que estabelece o comportamento básico de quem pretende escutar
 * pelos eventos de um bean de edição.
 *
 * @author Luiz Augusto
 */
public interface EditingListener {
    /**
     * Notificação da persistência de um objeto que foi salvo na tela de edição.
     * @param entity
     * @return
     */
    public String entitySaved(Entity entity);

    /**
     * Notificação do cancelamento da edição.
     * @return
     */
    public String editingCanceled();
}
