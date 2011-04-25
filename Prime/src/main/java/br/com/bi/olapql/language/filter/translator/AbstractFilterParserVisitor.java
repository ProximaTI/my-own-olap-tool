/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.filter.translator;

import br.com.bi.olapql.language.filter.AdditiveExpression;
import br.com.bi.olapql.language.filter.AndCondition;
import br.com.bi.olapql.language.filter.Compare;
import br.com.bi.olapql.language.filter.Condition;
import br.com.bi.olapql.language.filter.DateLiteral;
import br.com.bi.olapql.language.filter.EndsWithExpression;
import br.com.bi.olapql.language.filter.Filter;
import br.com.bi.olapql.language.filter.FilterExpression;
import br.com.bi.olapql.language.filter.FilterParserVisitor;
import br.com.bi.olapql.language.filter.InExpression;
import br.com.bi.olapql.language.filter.LikeExpression;
import br.com.bi.olapql.language.filter.MultiplicativeExpression;
import br.com.bi.olapql.language.filter.Negation;
import br.com.bi.olapql.language.filter.Node;
import br.com.bi.olapql.language.filter.NumberLiteral;
import br.com.bi.olapql.language.filter.OrCondition;
import br.com.bi.olapql.language.filter.Property;
import br.com.bi.olapql.language.filter.SimpleNode;
import br.com.bi.olapql.language.filter.StartsWithExpression;
import br.com.bi.olapql.language.filter.StringLiteral;

/**
 *
 * @author luiz
 */
public abstract class AbstractFilterParserVisitor implements FilterParserVisitor {

    public void visit(Node node, StringBuilder data) {
        if (node instanceof FilterExpression) {
            visit((FilterExpression) node, data);
        }
        if (node instanceof OrCondition) {
            visit((OrCondition) node, data);
        }
        if (node instanceof AndCondition) {
            visit((AndCondition) node, data);
        }
        if (node instanceof Condition) {
            visit((Condition) node, data);
        }
        if (node instanceof Negation) {
            visit((Negation) node, data);
        }
        if (node instanceof InExpression) {
            visit((InExpression) node, data);
        }
        if (node instanceof LikeExpression) {
            visit((LikeExpression) node, data);
        }
        if (node instanceof StartsWithExpression) {
            visit((StartsWithExpression) node, data);
        }
        if (node instanceof EndsWithExpression) {
            visit((EndsWithExpression) node, data);
        }
        if (node instanceof Compare) {
            visit((Compare) node, data);
        }
        if (node instanceof AdditiveExpression) {
            visit((AdditiveExpression) node, data);
        }
        if (node instanceof MultiplicativeExpression) {
            visit((MultiplicativeExpression) node, data);
        }
        if (node instanceof NumberLiteral) {
            visit((NumberLiteral) node, data);
        }
        if (node instanceof DateLiteral) {
            visit((DateLiteral) node, data);
        }
        if (node instanceof StringLiteral) {
            visit((StringLiteral) node, data);
        }
        if (node instanceof Filter) {
            visit((Filter) node, data);
        }
        if (node instanceof Property) {
            visit((Property) node, data);
        }
    }

    private void visitChildren(SimpleNode node, StringBuilder data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }

    @Override
    public void visit(SimpleNode node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(FilterExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(OrCondition node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(AndCondition node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Condition node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Negation node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(InExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(LikeExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(StartsWithExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(EndsWithExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Compare node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(AdditiveExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(MultiplicativeExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(NumberLiteral node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(DateLiteral node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(StringLiteral node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        visitChildren(node, data);
    }
}
