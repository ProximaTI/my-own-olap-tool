package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;

/**
 * Classe que estabele o comportamento básico de um bean de edição de objetos.
 *
 * @author Luiz Augusto
 */
public abstract class AbstractEditingBean extends AbstractManagedBean {
    private Entity entity;

    private EditingListener listener;

    /**
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @param entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * @return
     */
    public EditingListener getListener() {
        return listener;
    }

    /**
     * @param listener
     */
    public void setListener(EditingListener listener) {
        this.listener = listener;
    }

    // =========================
    // ======== eventos ========
    // =========================

    /**
     * Método que responde pela solicitação de persistência do objeto editado
     * e se este evento ocorrer sem exceções, avisa o listener da ocorrência
     * de tal evento.
     * @return
     */
    public String doSave() {
        try {
            save(getEntity());
        } catch (Exception e) {
            return null;
        }
        return listener.entitySaved(getEntity());
    }

    /**
     * Método que é responsável pela persistência do objeto em si e que deve
     * ser implementado por todos beans de edição.
     * @param entity
     * @throws Exception
     */
    public abstract void save(Entity entity) throws Exception;

    /**
     * Avisa o listener da ocorrência do cancelamento da edição.
     * @return
     */
    public String cancel() {
        return listener.editingCanceled();
    }
}
