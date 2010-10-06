package br.com.bi.model.entity.queries;

import java.io.Serializable;


public class NodeMember implements Serializable {

    private Integer id;

    private String memberName;

    private Node node;

    public NodeMember() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String name) {
        this.memberName = name;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node no) {
        this.node = no;
    }
}
