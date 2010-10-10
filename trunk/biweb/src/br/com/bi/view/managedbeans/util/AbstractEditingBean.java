package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;

/**
 * Classe que estabele o comportamento b�sico de um bean de edi��o de objetos.
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
     * M�todo que responde pela solicita��o de persist�ncia do objeto editado
     * e se este evento ocorrer sem exce��es, avisa o listener da ocorr�ncia
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
     * M�todo que � respons�vel pela persist�ncia do objeto em si e que deve
     * ser implementado por todos beans de edi��o.
     * @param entity
     * @throws Exception
     */
    public abstract void save(Entity entity) throws Exception;

    /**
     * Avisa o listener da ocorr�ncia do cancelamento da edi��o.
     * @return
     */
    public String cancel() {
        return listener.editingCanceled();
    }
}
