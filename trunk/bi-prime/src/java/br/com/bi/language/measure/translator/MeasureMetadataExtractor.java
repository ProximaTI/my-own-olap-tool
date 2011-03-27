/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.measure.translator;

import br.com.bi.language.filter.translator.FilterMetadataExtractor;
import br.com.bi.language.measure.Measure;
import br.com.bi.language.measure.MeasureParser;
import br.com.bi.language.measure.ParseException;
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
public class MeasureMetadataExtractor extends AbstractMeasureParserVisitor {

    private MetadataCache extracted = new MetadataCache();

    public MetadataCache extract(String expression) {
        MeasureParser parser = new MeasureParser(IOUtils.toInputStream(expression));

        try {
            visit(parser.measureExpression(), null);
        } catch (ParseException ex) {
            Logger.getLogger(MeasureMetadataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return extracted;
    }

    @Override
    public void visit(Measure node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        MeasureMetadataExtractor measureExtractor = new MeasureMetadataExtractor();
        FilterMetadataExtractor filterExtractor = new FilterMetadataExtractor();

        if (measure != null) {
            extracted.put(node.jjtGetValue().toString(), measure);

            extracted.put(measureExtractor.extract(measure.getExpression()));

            if (measure.getFilterExpression() != null) {
                extracted.put(filterExtractor.extract(measure.getFilterExpression()));
            }
        }
    }
}
