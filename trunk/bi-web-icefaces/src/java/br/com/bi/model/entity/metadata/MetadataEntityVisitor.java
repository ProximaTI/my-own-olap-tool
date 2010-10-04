/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.entity.metadata;

/**
 *
 * @author Luiz
 */
public interface MetadataEntityVisitor {

    public void visit(Metrica metrica);

    public void visit(Filtro filtro);

    public void visit(Nivel nivel);
}
