/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.server.olapql.language.utils.MetadataBag;
import br.com.proximati.biprime.server.olapql.language.measure.translator.MeasureMetadataExtractor;
import br.com.proximati.biprime.server.olapql.language.utils.TranslationUtils;
import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Measure;
import br.com.proximati.biprime.metadata.entity.Property;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevel;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTProperty;
import br.com.proximati.biprime.server.olapql.language.query.ASTPropertyNode;

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
    public void visit(ASTLevelOrMeasureOrFilter node, StringBuilder data) {
        Measure measure = Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (measure != null) {
            getAddedToAxis().put(node.jjtGetValue().toString(), measure);
            MeasureMetadataExtractor measureExtractor = new MeasureMetadataExtractor();
            getIndirectlyAdded().put(measureExtractor.extractFromObject(measure.getName()));
        } else {
            Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
            if (level != null) {
                getAddedToAxis().put(node.jjtGetValue().toString(), level);
            } else {
                Filter filter = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
                getAddedToAxis().put(node.jjtGetValue().toString(), filter);
                getIndirectlyAdded().put(extractFromFilter(filter.getName()));
            }
        }

        super.visit(node, data);
    }

    @Override
    public void visit(ASTPropertyNode node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");
        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));
        Property property = level.getProperty(TranslationUtils.extractName(str[1]));
        getAddedToAxis().put(node.jjtGetValue().toString(), property);
        super.visit(node, data);
    }

    @Override
    public void visit(ASTProperty node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");
        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));
        Property property = level.getProperty(TranslationUtils.extractName(str[1]));
        getAddedToFilter().put(node.jjtGetValue().toString(), property);
    }

    @Override
    public void visit(ASTLevel node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();
        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
        if (level != null) {
            getAddedToFilter().put(nodeValue, level);
        }
    }

    @Override
    public void visit(ASTFilter node, StringBuilder data) {
        String nodeValue = node.jjtGetValue().toString();
        Filter filter = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
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
