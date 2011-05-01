/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.measure.translator;

import br.com.proximati.biprime.server.olapql.language.measure.Measure;
import br.com.proximati.biprime.server.olapql.language.measure.MeasureParser;
import br.com.proximati.biprime.server.olapql.language.utils.MetadataBag;
import br.com.proximati.biprime.server.olapql.language.utils.TranslationUtils;
import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.server.olapql.language.query.translator.FilterMetadataExtractor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luiz
 */
public class MeasureMetadataExtractor extends AbstractMeasureParserVisitor {

    private MetadataBag bag;

    @Override
    public void visit(Measure node, StringBuilder data) {
        br.com.proximati.biprime.metadata.entity.Measure measure =
                Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (measure != null) {
            bag.put(node.jjtGetValue().toString(), measure);

            MeasureMetadataExtractor extractor = new MeasureMetadataExtractor();
            bag.put(extractor.extractFromExpression(measure.getExpression()));
            bag.put(extractor.extractFromFilterExpression(measure));
        }
    }

    /**
     * Retorna os metadados referenciados por uma expressão de filtro.
     * @param measure
     * @return
     */
    MetadataBag extractFromFilterExpression(br.com.proximati.biprime.metadata.entity.Measure measure) {
        FilterMetadataExtractor extractor = new FilterMetadataExtractor();
        return extractor.extractFromExpression(measure.getFilterExpression());
    }

    /**
     * Retorna os metadados referenciados por uma métrica.
     * @param name
     * @return
     */
    public MetadataBag extractFromObject(String name) {
        bag = new MetadataBag();
        try {
            br.com.proximati.biprime.metadata.entity.Measure measure = Application.getMeasureDao().findByName(name);
            MeasureParser parser = new MeasureParser(IOUtils.toInputStream(measure.getExpression()));
            visit(parser.measureExpression(), null);
            bag.put(extractFromFilterExpression(measure));
        } catch (Exception ex) {
            Logger.getLogger(FilterMetadataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return bag;
        }
    }

    /**
     * Retorna os metadados referenciados por uma expressão de métrica.
     * @param expression
     * @return
     */
    public MetadataBag extractFromExpression(String expression) {
        bag = new MetadataBag();
        try {
            MeasureParser parser = new MeasureParser(IOUtils.toInputStream(expression));
            visit(parser.measureExpression(), null);
            return bag;
        } catch (Exception ex) {
            Logger.getLogger(FilterMetadataExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return bag;
        }
    }
}
