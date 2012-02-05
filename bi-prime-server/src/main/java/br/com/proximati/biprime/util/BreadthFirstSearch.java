/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.util;

import br.com.proximati.biprime.server.olapql.query.result.Node;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Classe que faz caminhamento Breadth-First Search em uma árvore de nós,
 * partindo da raiz até as folhas.
 *
 * Como nossa árvore é um grafo direcionado acíclico, não precisamos
 * utilizar coloração (white, gray, black) nos vértices.
 * @author rnpcapes
 */
public class BreadthFirstSearch {

    private TraversingListener listener;

    public BreadthFirstSearch(TraversingListener listener) {
        this.listener = listener;
    }

    public void perform(Node s) {
        listener.visitingRoot(s);

        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(s);

        while (queue.size() != 0) {
            Node u = queue.poll();

            if (u.getChildrenNodes().isEmpty())
                listener.visitingLeaf(s, u);
            else {
                if (u != s)
                    listener.visitingNonLeaf(s, u);
                for (Node v : u.getChildrenNodes())
                    queue.offer(v);
            }
        }
    }
}
