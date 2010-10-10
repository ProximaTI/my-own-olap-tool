package br.com.bi.model.translator.queries;


import br.com.bi.model.entity.queries.Node;

import org.olap4j.mdx.CallNode;
import org.olap4j.mdx.IdentifierNode;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.mdx.Syntax;


class Util {
    static final String MEMBERS = "Members";
    static final String BRACES = "{}";

    static protected ParseTreeNode buildLevelNode(Node node) {
        return new CallNode(null, MEMBERS, Syntax.Property,
                            new IdentifierNode(IdentifierNode.parseIdentifier(node.getAssociateElement())));
    }

    static protected ParseTreeNode buildMeasureNode(Node node) {
        return new CallNode(null, BRACES, Syntax.Braces,
                            new IdentifierNode(IdentifierNode.parseIdentifier(node.getAssociateElement())));
    }

    static ParseTreeNode buildMemberNode(Node node) {
        return new CallNode(null, BRACES, Syntax.Braces,
                            new IdentifierNode(IdentifierNode.parseIdentifier(node.getAssociateElement())));
    }
}
