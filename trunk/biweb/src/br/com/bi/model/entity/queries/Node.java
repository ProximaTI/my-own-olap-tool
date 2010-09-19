package br.com.bi.model.entity.queries;


import br.com.bi.model.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Classe que representa um nó numa árvore que representa uma consulta
 * multidimensional.
 *
 * @author Luiz Augusto Garcia da Silva
 */
public class Node extends Entity {
    public static final int TYPE_LEVEL = 1;
    public static final int TYPE_MEMBER = 2;
    public static final int TYPE_MEMBER_SET = 3;
    public static final int TYPE_MEASURE = 4;

    private String associateElement;
    private Integer id;
    private String name;
    private int type;
    private Integer indice;
    private Node parentNode;
    private List<Node> children;

    public Node() {
    }

    public Node(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, int type, String associateElement) {
        this.name = name;
        this.type = type;
        this.associateElement = associateElement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idno) {
        this.id = idno;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public List<Node> getChildren() {
        if (children == null)
            children = new ArrayList<Node>();

        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node noSuperior) {
        this.parentNode = noSuperior;
    }

    public String getAssociateElement() {
        return associateElement;
    }

    public void setAssociateElement(String associateElement) {
        this.associateElement = associateElement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLevel() {
        return getType() == TYPE_LEVEL;
    }

    public boolean isMeasure() {
        return getType() == TYPE_MEASURE;
    }

    public boolean isMember() {
        return getType() == TYPE_MEMBER;
    }

    public void addChild(Node node) {
        getChildren().add(node);
        node.setParentNode(this);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public Node clone() {
        return internalClone(this, new HashMap<Integer, Node>());
    }

    private Node internalClone(Node cloned, Map<Integer, Node> nodeCache) {
        Node clone = new Node(cloned.getId(), cloned.getName());

        nodeCache.put(clone.getId(), clone);

        clone.setAssociateElement(cloned.getAssociateElement());
        clone.setIndice(cloned.getIndice());
        clone.setParentNode(cloned.getParentNode() == null ? null :
                            nodeCache.get(cloned.getParentNode().getId()));
        clone.setType(cloned.getType());

        clone.setChildren(new ArrayList<Node>());

        for (Node child : cloned.getChildren()) {
            clone.getChildren().add(internalClone(child, nodeCache));
        }

        return clone;
    }
}
