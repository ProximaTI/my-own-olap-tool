/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Property;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTProperty;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.utils.MetadataBag;
import br.com.proximati.biprime.server.olapql.language.utils.TranslationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author luiz
 */
public class FilterMetadataExtractor extends AbstractQueryVisitor {

    private MetadataBag bag;

    @Override
    public void visit(ASTFilter node, StringBuilder data) throws Exception {
        String nodeValue = node.jjtGetValue().toString();

        Filter filter = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (filter != null) {
            bag.put(nodeValue, filter);

            bag.put(extractFromExpression(filter.getExpression()));
        }
    }

    @Override
    public void visit(ASTProperty node, StringBuilder data) throws Exception {
        String[] str = node.jjtGetValue().toString().split("\\.");

        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        Property property =
                level.getProperty(TranslationUtils.extractName(str[1]));

        bag.put(node.jjtGetValue().toString(), property);
    }

    /**
     * Extrai os metadados referenciados por um filtro.
     * @param name
     * @return
     */
    public MetadataBag extractFromObject(String name) throws Exception {
        bag = new MetadataBag();
        Filter filter = Application.getFilterDao().findByName(name);
        QueryParser parser = new QueryParser(IOUtils.toInputStream(filter.getExpression()));
        visit(parser.detachedFilterExpression(), null);
        return bag;
    }

    /**
     * Extrai os metadados referenciados por uma expressão de filtro.
     * @param expression
     * @return
     */
    public MetadataBag extractFromExpression(String expression) throws Exception {
        bag = new MetadataBag();
        if (StringUtils.isNotBlank(expression)) {
            QueryParser parser = new QueryParser(IOUtils.toInputStream(expression));
            visit(parser.detachedFilterExpression(), null);
        }
        return bag;
    }
}
