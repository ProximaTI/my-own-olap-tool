package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;

/**
 * Interface que estabelece o comportamento b�sico de quem pretende escutar
 * pelos eventos de um bean de edi��o.
 *
 * @author Luiz Augusto
 */
public interface EditingListener {
    /**
     * Notifica��o da persist�ncia de um objeto que foi salvo na tela de edi��o.
     * @param entity
     * @return
     */
    public String entitySaved(Entity entity);

    /**
     * Notifica��o do cancelamento da edi��o.
     * @return
     */
    public String editingCanceled();
}
