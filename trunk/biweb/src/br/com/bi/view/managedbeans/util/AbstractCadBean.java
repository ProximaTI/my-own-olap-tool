package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;


/**
 * Classe que re�ne as caracter�sticas b�sicas de um bean de cadastro.
 *
 * @author Luiz Augusto
 */
public abstract class AbstractCadBean extends AbstractManagedBean {
    private CadListener listener;

    /**
     * @return
     */
    public CadListener getListener() {
        return listener;
    }

    /**
     * @param listener
     */
    public void setListener(CadListener listener) {
        this.listener = listener;
    }

    // ====================
    // ====== modelo ======
    // ====================

    /**
     * Retorna o objeto selecionado na tabela do cadastro.
     * @return
     */
    public abstract Entity getSelectedEntity();

    // ======================
    // ======= eventos ======
    // ======================

    /**
     * M�todo que responde pela solicita��o de inclus�o de um novo
     * objeto.
     * @return
     */
    public String insert() {
        return listener.insert();
    }

    /**
     * Responde pela edi��o de do objeto selecionado na tabela.
     * @return
     */
    public String edit() {
        try {
            return listener.edit(getSelectedEntity().clone());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Responde pela exclus�o de um objeto.
     * @return
     */
    public abstract String delete();
}
