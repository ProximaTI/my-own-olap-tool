/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.server.olapql.language.query.AdditiveExpression;
import br.com.proximati.biprime.server.olapql.language.query.AndCondition;
import br.com.proximati.biprime.server.olapql.language.query.Axis;
import br.com.proximati.biprime.server.olapql.language.query.Compare;
import br.com.proximati.biprime.server.olapql.language.query.Condition;
import br.com.proximati.biprime.server.olapql.language.query.Cube;
import br.com.proximati.biprime.server.olapql.language.query.DateLiteral;
import br.com.proximati.biprime.server.olapql.language.query.DetachedFilterExpression;
import br.com.proximati.biprime.server.olapql.language.query.EndsWithExpression;
import br.com.proximati.biprime.server.olapql.language.query.Filter;
import br.com.proximati.biprime.server.olapql.language.query.FilterExpression;
import br.com.proximati.biprime.server.olapql.language.query.InExpression;
import br.com.proximati.biprime.server.olapql.language.query.Instruction;
import br.com.proximati.biprime.server.olapql.language.query.Level;
import br.com.proximati.biprime.server.olapql.language.query.LevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.LikeExpression;
import br.com.proximati.biprime.server.olapql.language.query.MultiplicativeExpression;
import br.com.proximati.biprime.server.olapql.language.query.Negation;
import br.com.proximati.biprime.server.olapql.language.query.Node;
import br.com.proximati.biprime.server.olapql.language.query.NumberLiteral;
import br.com.proximati.biprime.server.olapql.language.query.OrCondition;
import br.com.proximati.biprime.server.olapql.language.query.Property;
import br.com.proximati.biprime.server.olapql.language.query.PropertyNode;
import br.com.proximati.biprime.server.olapql.language.query.QueryParserVisitor;
import br.com.proximati.biprime.server.olapql.language.query.Select;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import br.com.proximati.biprime.server.olapql.language.query.StartsWithExpression;
import br.com.proximati.biprime.server.olapql.language.query.StringLiteral;

/**
 *
 * @author luiz
 */
public abstract class AbstractQueryVisitor implements QueryParserVisitor {

    public void visit(Node node, StringBuilder data) {
        if (node instanceof Instruction) {
            visit((Instruction) node, data);
        }
        if (node instanceof Select) {
            visit((Select) node, data);
        }
        if (node instanceof DetachedFilterExpression) {
            visit((DetachedFilterExpression) node, data);
        }
        if (node instanceof FilterExpression) {
            visit((FilterExpression) node, data);
        }
        if (node instanceof Axis) {
            visit((Axis) node, data);
        }
        if (node instanceof LevelOrMeasureOrFilter) {
            visit((LevelOrMeasureOrFilter) node, data);
        }
        if (node instanceof PropertyNode) {
            visit((PropertyNode) node, data);
        }
        if (node instanceof Level) {
            visit((Level) node, data);
        }
        if (node instanceof Filter) {
            visit((Filter) node, data);
        }
        if (node instanceof Property) {
            visit((Property) node, data);
        }
        if (node instanceof Cube) {
            visit((Cube) node, data);
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
    }

    @Override
    public void visit(SimpleNode node, StringBuilder data) {
        visit((Node) node, data);
    }

    @Override
    public void visit(Instruction node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Select node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Axis node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(PropertyNode node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Cube node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(FilterExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Negation node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Level node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        visitChildren(node, data);
    }

    @Override
    public void visit(StringLiteral node, StringBuilder data) {
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
    public void visit(DetachedFilterExpression node, StringBuilder data) {
        visitChildren(node, data);
    }

    protected Integer childIndex(Node node) {
        for (int i = 0; i < node.jjtGetParent().jjtGetNumChildren(); i++) {
            if (node.jjtGetParent().jjtGetChild(i).equals(node)) {
                return i;
            }
        }
        return -1;
    }

    protected void visitChildren(SimpleNode node, StringBuilder data) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            visit(node.jjtGetChild(i), data);
        }
    }
}
