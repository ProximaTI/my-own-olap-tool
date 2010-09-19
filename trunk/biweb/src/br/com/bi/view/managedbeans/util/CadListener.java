package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;

/**
 * Interface que estabelece os m�todos que devem implementar as classes
 * que pretendem escutar os eventos de um bean de cadastro.
 *
 * @author Luiz Augusto
 */
public interface CadListener {
    /**
     * Responde pela solicita��o da inclus�o de um novo objeto.
     * @return
     */
    public String insert();

    /**
     * Responde pela solicita��o da edi��o de um objeto j� persistido.
     * @param entity
     * @return
     */
    public String edit(Entity entity);
}
