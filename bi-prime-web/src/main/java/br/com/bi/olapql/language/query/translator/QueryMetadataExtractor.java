/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.query.translator;

import br.com.bi.olapql.language.utils.MetadataBag;
import br.com.bi.olapql.language.measure.translator.MeasureMetadataExtractor;
import br.com.bi.olapql.language.query.Filter;
import br.com.bi.olapql.language.query.Level;
import br.com.bi.olapql.language.query.LevelOrMeasureOrFilter;
import br.com.bi.olapql.language.query.Property;
import br.com.bi.olapql.language.query.PropertyNode;
import br.com.bi.olapql.language.utils.TranslationUtils;
import br.com.bi.model.Application;

/**
 * Classe que tem como responsabilidade extrair os metadados referenciados
 * nos eixos de uma consulta. Ele tem a capacidade de encontrar tanto os 
 * metadados que são referenciados diretamente nos eixos, quanto indiretamente,
 * através de um métrica ou de um filtro.
 * 
 * @author luiz
 */
public class QueryMetadataExtractor extends AbstractQueryVisitor {

    /* holds metadata directly added to both rows and columns axis */
    private MetadataBag addedToAxis = new MetadataBag();
    /* holds metadata referenced by some filter, or measure filter added as axis node */
    private MetadataBag indirectlyAdded = new MetadataBag();
    /* holds metadata referenced by query filter */
    private MetadataBag addedToFilter = new MetadataBag();

    @Override
    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (measure != null) {
            getAddedToAxis().put(node.jjtGetValue().toString(), measure);
            MeasureMetadataExtractor measureExtractor = new MeasureMetadataExtractor();
            getIndirectlyAdded().put(measureExtractor.extractFromObject(measure.getName()));
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
            if (level != null) {
                getAddedToAxis().put(node.jjtGetValue().toString(), level);
            } else {
                br.com.bi.model.entity.metadata.Filter filter =
                        Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
                getAddedToAxis().put(node.jjtGetValue().toString(), filter);
                getIndirectlyAdded().put(extractFromFilter(filter.getName()));
            }
        }

        super.visit(node, data);
    }

    @Override
    public void visit(PropertyNode node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property = level.getProperty(TranslationUtils.extractName(str[1]));
        getAddedToAxis().put(node.jjtGetValue().toString(), property);

        super.visit(node, data);
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property =
                level.getProperty(TranslationUtils.extractName(str[1]));
        getAddedToFilter().put(node.jjtGetValue().toString(), property);
    }

    @Override
    public void visit(Level node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (level != null) {
            getAddedToFilter().put(nodeValue, level);
        }
    }

    @Override
    public void visit(Filter node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();

        br.com.bi.model.entity.metadata.Filter filter =
                Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (filter != null) {
            getAddedToFilter().put(nodeValue, filter);
            getAddedToFilter().put(extractFromFilter(filter.getName()));
        }
    }

    /**
     * Retorna a união de todos os metadados referenciados.
     * @return
     */
    public MetadataBag getAllReferencedMetadata() {
        MetadataBag bag = new MetadataBag();
        bag.put(getAddedToAxis());
        bag.put(getAddedToFilter());
        bag.put(getIndirectlyAdded());

        return bag;
    }

    /**
     * Extrai os metadados refereciados por um filtro.
     * @param name
     * @return
     */
    private MetadataBag extractFromFilter(String name) {
        FilterMetadataExtractor extractor = new FilterMetadataExtractor();
        return extractor.extractFromObject(name);
    }

    /**
     * @return the addedToAxis
     */
    public MetadataBag getAddedToAxis() {
        return addedToAxis;
    }

    /**
     * @return the indirectlyAdded
     */
    public MetadataBag getIndirectlyAdded() {
        return indirectlyAdded;
    }

    /**
     * @return the addedToFilter
     */
    public MetadataBag getAddedToFilter() {
        return addedToFilter;
    }
}
