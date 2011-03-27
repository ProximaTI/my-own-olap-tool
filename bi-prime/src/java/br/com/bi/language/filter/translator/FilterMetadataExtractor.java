/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.filter.translator;

import br.com.bi.language.filter.Filter;
import br.com.bi.language.filter.FilterParser;
import br.com.bi.language.filter.LevelOrMeasure;
import br.com.bi.language.filter.Measure;
import br.com.bi.language.filter.ParseException;
import br.com.bi.language.filter.Property;
import br.com.bi.language.measure.translator.MeasureMetadataExtractor;
import br.com.bi.language.utils.MetadataCache;
import br.com.bi.language.utils.TranslationUtils;
import br.com.bi.model.Application;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luiz
 */
public class FilterMetadataExtractor extends AbstractFilterParserVisitor {

    private MetadataCache extracted = new MetadataCache();

    public MetadataCache extract(String filterExpression) {
        FilterParser parser = new FilterParser(IOUtils.toInputStream(filterExpression));

        try {
            visit(parser.filterExpression(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MeasureMetadataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return extracted;
    }

    @Override
    public void visit(Measure node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        MeasureMetadataExtractor mExt = new MeasureMetadataExtractor();
        FilterMetadataExtractor fExt = new FilterMetadataExtractor();

        if (measure != null) {
            extracted.put(node.jjtGetValue().toString(), measure);

            extracted.put(mExt.extract(measure.getExpression()));
            extracted.put(fExt.extract(measure.getFilterExpression()));
        }
    }

    @Override
    public void visit(LevelOrMeasure node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();

        br.com.bi.model.entity.metadata.Measure measure = Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        MeasureMetadataExtractor mExt = new MeasureMetadataExtractor();
        FilterMetadataExtractor fExt = new FilterMetadataExtractor();

        if (measure != null) {
            extracted.put(nodeValue, measure);

            extracted.put(mExt.extract(measure.getExpression()));
            extracted.put(fExt.extract(measure.getFilterExpression()));
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

            if (level != null) {
                extracted.put(nodeValue, level);
            }
        }
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Filter filter =
                Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        FilterMetadataExtractor fExt = new FilterMetadataExtractor();

        extracted.put(node.jjtGetValue().toString(), filter);
        extracted.put(fExt.extract(filter.getExpression()));
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property = level.getProperty(TranslationUtils.extractName(str[1]));

        extracted.put(node.jjtGetValue().toString(), property);
    }
}
