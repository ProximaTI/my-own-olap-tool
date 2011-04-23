/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.filter.translator;

import br.com.bi.olapql.language.filter.Filter;
import br.com.bi.olapql.language.filter.FilterParser;
import br.com.bi.olapql.language.filter.Level;
import br.com.bi.olapql.language.filter.ParseException;
import br.com.bi.olapql.language.filter.Property;
import br.com.bi.olapql.language.measure.translator.MeasureMetadataExtractor;
import br.com.bi.olapql.language.utils.MetadataCache;
import br.com.bi.olapql.language.utils.TranslationUtils;
import br.com.bi.model.Application;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luiz
 */
public class FilterMetadataExtractor extends AbstractFilterParserVisitor {

    private MetadataCache extractedMetadata = new MetadataCache();

    public MetadataCache extract(String filterExpression) {
        FilterParser parser = new FilterParser(IOUtils.toInputStream(filterExpression));

        try {
            visit(parser.filterExpression(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MeasureMetadataExtractor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return extractedMetadata;
    }

    @Override
    public void visit(Level node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (level != null) {
            extractedMetadata.put(nodeValue, level);
        }
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Filter filter =
                Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        FilterMetadataExtractor fExt = new FilterMetadataExtractor();

        extractedMetadata.put(node.jjtGetValue().toString(), filter);
        extractedMetadata.put(fExt.extract(filter.getExpression()));
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property = level.getProperty(TranslationUtils.extractName(str[1]));

        extractedMetadata.put(node.jjtGetValue().toString(), property);
    }
}
