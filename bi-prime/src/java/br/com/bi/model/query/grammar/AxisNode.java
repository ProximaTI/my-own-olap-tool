/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.model.query.grammar;

/**
 *
 * @author luiz
 */
public class AxisNode extends SimpleNode {

    private String name;

    public AxisNode(int i) {
        super(i);
    }

    public AxisNode(OlapQlParser p, int i) {
        super(p, i);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
