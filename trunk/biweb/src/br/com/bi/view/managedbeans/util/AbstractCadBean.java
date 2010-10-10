package br.com.bi.view.managedbeans.util;

import br.com.bi.model.entity.Entity;


/**
 * Classe que reúne as características básicas de um bean de cadastro.
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
     * Método que responde pela solicitação de inclusão de um novo
     * objeto.
     * @return
     */
    public String insert() {
        return listener.insert();
    }

    /**
     * Responde pela edição de do objeto selecionado na tabela.
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
     * Responde pela exclusão de um objeto.
     * @return
     */
    public abstract String delete();
}
