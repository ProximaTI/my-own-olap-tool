/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.util;

import br.com.proximati.biprime.server.olapql.query.result.Node;

/**
 * Responde pelos eventos ocorridos durante caminhamento de uma
 * árvore de nós.
 * 
 * @author rnpcapes
 */
public interface TraversingListener {

    /**
     * Visitação do nó raiz.
     * @param s
     */
    public void visitingRoot(Node s);

    /**
     * Visitação de uma folha.
     * @param s
     * @param u
     */
    public void visitingLeaf(Node s, Node u);

    /**
     * Visitação de um nó não folha.
     * @param s
     * @param v
     */
    public void visitingNonLeaf(Node s, Node v);
}
