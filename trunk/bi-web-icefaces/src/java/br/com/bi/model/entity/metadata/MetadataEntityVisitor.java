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

    public void visit(Measure metrica);

    public void visit(Filter filtro);

    public void visit(Level nivel);
}
