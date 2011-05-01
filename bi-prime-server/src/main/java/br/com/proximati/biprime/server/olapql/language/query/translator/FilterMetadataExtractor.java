/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.server.olapql.language.query.Filter;
import br.com.proximati.biprime.server.olapql.language.query.ParseException;
import br.com.proximati.biprime.server.olapql.language.query.Property;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.utils.MetadataBag;
import br.com.proximati.biprime.server.olapql.language.utils.TranslationUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author luiz
 */
public class FilterMetadataExtractor extends AbstractQueryVisitor {

    private MetadataBag bag;

    @Override
    public void visit(Filter node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();

        br.com.proximati.biprime.metadata.entity.Filter filter =
                Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (filter != null) {
            bag.put(nodeValue, filter);

            FilterMetadataExtractor extractor = new FilterMetadataExtractor();
            bag.put(extractFromExpression(filter.getExpression()));
        }
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.proximati.biprime.metadata.entity.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.proximati.biprime.metadata.entity.Property property =
                level.getProperty(TranslationUtils.extractName(str[1]));

        bag.put(node.jjtGetValue().toString(), property);
    }

    /**
     * Extrai os metadados referenciados por um filtro.
     * @param name
     * @return
     */
    public MetadataBag extractFromObject(String name) {
        bag = new MetadataBag();
        try {
            br.com.proximati.biprime.metadata.entity.Filter filter = Application.getFilterDao().findByName(name);
            QueryParser parser = new QueryParser(IOUtils.toInputStream(filter.getExpression()));
            visit(parser.detachedFilterExpression(), null);
        } catch (ParseException ex) {
            Logger.getLogger(FilterMetadataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return bag;
        }
    }

    /**
     * Extrai os metadados referenciados por uma expressão de filtro.
     * @param expression
     * @return
     */
    public MetadataBag extractFromExpression(String expression) {
        bag = new MetadataBag();
        if (StringUtils.isNotBlank(expression)) {
            try {
                QueryParser parser = new QueryParser(IOUtils.toInputStream(expression));
                visit(parser.detachedFilterExpression(), null);
                return bag;
            } catch (ParseException ex) {
                Logger.getLogger(FilterMetadataExtractor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bag;
    }
}
