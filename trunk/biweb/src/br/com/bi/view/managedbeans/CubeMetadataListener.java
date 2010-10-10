package br.com.bi.view.managedbeans;

import org.olap4j.metadata.Cube;

/**
 * Interface que estabelece os métodos que um listener do modelo de
 * metadados de um cubo deve implementar.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public interface CubeMetadataListener {
    /**
     * Notificação da mudança do cubo selecionado.
     * @param selectedCube
     */
    public void selectedCubeChanged(Cube selectedCube);
}
