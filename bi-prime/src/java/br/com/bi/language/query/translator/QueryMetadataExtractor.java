/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.language.query.translator;

import br.com.bi.language.utils.MetadataCache;
import br.com.bi.language.filter.translator.FilterMetadataExtractor;
import br.com.bi.language.measure.translator.MeasureMetadataExtractor;
import br.com.bi.language.query.LevelOrMeasureOrFilter;
import br.com.bi.language.query.Property;
import br.com.bi.language.utils.TranslationUtils;
import br.com.bi.model.Application;
import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.model.entity.metadata.Measure;

/**
 * Classe que tem como responsabilidade extrair os metadados referenciados
 * nos eixos de uma consulta. Ele tem a capacidade de encontrar tanto os 
 * metadados que são referenciados diretamente nos eixos, quanto indiretamente,
 * através de um métrica ou de um filtro.
 * 
 * @author luiz
 */
public class QueryMetadataExtractor extends AbstractQueryVisitor {

    private MetadataCache extractedMetadata = new MetadataCache();

    public MetadataCache getExtractedMetadata() {
        return extractedMetadata;
    }

    @Override
    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();

        Measure measure = Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        MeasureMetadataExtractor measureExtractor = new MeasureMetadataExtractor();
        FilterMetadataExtractor filterExtractor = new FilterMetadataExtractor();

        if (measure != null) {
            extractedMetadata.put(nodeValue, measure);

            extractedMetadata.put(measureExtractor.extract(measure.getExpression()));

            if (measure.getFilterExpression() != null) {
                extractedMetadata.put(filterExtractor.extract(measure.getFilterExpression()));
            }
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

            if (level != null) {
                extractedMetadata.put(nodeValue, level);
            } else {
                Filter filter = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
                extractedMetadata.put(nodeValue, filter);
                extractedMetadata.put(filterExtractor.extract(filter.getExpression()));
            }
        }
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
