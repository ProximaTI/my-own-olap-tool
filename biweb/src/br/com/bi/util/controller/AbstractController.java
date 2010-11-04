package br.com.bi.util.controller;

import br.com.bi.util.view.jsf.Util;

public abstract class AbstractController {
    /**
     * Retorna o nome do bean que corresponde à este controlador.
     * @return
     */
    public abstract String getBeanName();

    /**
     * Método responsável por dispor os recursos utilizados por este controlador.
     */
    public void dispose() {
        // TODO externalizar isso em "driver" de controle
        Util.discardSubmittedValues();
    }
}
