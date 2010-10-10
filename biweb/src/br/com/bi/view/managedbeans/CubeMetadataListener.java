package br.com.bi.view.managedbeans;

import org.olap4j.metadata.Cube;

/**
 * Interface que estabelece os m�todos que um listener do modelo de
 * metadados de um cubo deve implementar.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public interface CubeMetadataListener {
    /**
     * Notifica��o da mudan�a do cubo selecionado.
     * @param selectedCube
     */
    public void selectedCubeChanged(Cube selectedCube);
}
