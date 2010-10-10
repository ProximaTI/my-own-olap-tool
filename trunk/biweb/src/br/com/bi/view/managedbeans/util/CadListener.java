package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;

/**
 * Interface que estabelece os métodos que devem implementar as classes
 * que pretendem escutar os eventos de um bean de cadastro.
 *
 * @author Luiz Augusto
 */
public interface CadListener {
    /**
     * Responde pela solicitação da inclusão de um novo objeto.
     * @return
     */
    public String insert();

    /**
     * Responde pela solicitação da edição de um objeto já persistido.
     * @param entity
     * @return
     */
    public String edit(Entity entity);
}
