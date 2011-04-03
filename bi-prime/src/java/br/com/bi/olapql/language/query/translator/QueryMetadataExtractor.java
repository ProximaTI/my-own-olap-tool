/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.query.translator;

import br.com.bi.olapql.language.utils.MetadataCache;
import br.com.bi.olapql.language.filter.translator.FilterMetadataExtractor;
import br.com.bi.olapql.language.measure.translator.MeasureMetadataExtractor;
import br.com.bi.olapql.language.query.Filter;
import br.com.bi.olapql.language.query.Level;
import br.com.bi.olapql.language.query.LevelOrMeasureOrFilter;
import br.com.bi.olapql.language.query.Node;
import br.com.bi.olapql.language.query.Property;
import br.com.bi.olapql.language.query.PropertyNode;
import br.com.bi.olapql.language.query.SimpleNode;
import br.com.bi.olapql.language.utils.TranslationUtils;
import br.com.bi.model.Application;
import br.com.bi.model.entity.metadata.Metadata;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que tem como responsabilidade extrair os metadados referenciados
 * nos eixos de uma consulta. Ele tem a capacidade de encontrar tanto os 
 * metadados que são referenciados diretamente nos eixos, quanto indiretamente,
 * através de um métrica ou de um filtro.
 * 
 * @author luiz
 */
public class QueryMetadataExtractor extends AbstractQueryVisitor {

    private MetadataCache addedToAxis = new MetadataCache();
    private MetadataCache indirectlyAdded = new MetadataCache();
    private MetadataCache addedToFilter = new MetadataCache();

    public MetadataCache getIndirectedAdded() {
        return getIndirectlyAdded();
    }

    public List<Node> getChildren(Node node) {
        List<Node> children = new ArrayList<Node>();

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            children.add(node.jjtGetChild(i));
        }

        return children;
    }

    @Override
    public void visit(LevelOrMeasureOrFilter node, StringBuilder data) {
        br.com.bi.model.entity.metadata.Measure measure =
                Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        MeasureMetadataExtractor measureExtractor = new MeasureMetadataExtractor();
        FilterMetadataExtractor filterExtractor = new FilterMetadataExtractor();

        if (measure != null) {
            recordNodeAddedToAxis(node, measure);

            getIndirectlyAdded().put(measureExtractor.extract(measure.getExpression()));

            if (measure.getFilterExpression() != null) {
                getIndirectlyAdded().put(filterExtractor.extract(measure.getFilterExpression()));
            }
        } else {
            br.com.bi.model.entity.metadata.Level level =
                    Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

            if (level != null) {
                recordNodeAddedToAxis(node, level);
            } else {
                br.com.bi.model.entity.metadata.Filter filter =
                        Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
                recordNodeAddedToAxis(node, filter);

                getIndirectlyAdded().put(filterExtractor.extract(filter.getExpression()));
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
        recordNodeAddedToAxis(node, property);

        super.visit(node, data);
    }

    @Override
    public void visit(Property node, StringBuilder data) {
        String[] str = node.jjtGetValue().toString().split("\\.");

        br.com.bi.model.entity.metadata.Level level =
                Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));

        br.com.bi.model.entity.metadata.Property property = level.getProperty(TranslationUtils.extractName(str[1]));
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

        FilterMetadataExtractor filterExtractor = new FilterMetadataExtractor();

        if (filter != null) {
            getAddedToFilter().put(nodeValue, filter);
            getAddedToFilter().put(filterExtractor.extract(filter.getExpression()));
        }
    }

    public MetadataCache getAllReferencedMetadata() {
        MetadataCache cache = new MetadataCache();
        cache.put(getAddedToAxis());
        cache.put(getAddedToFilter());
        cache.put(getIndirectlyAdded());

        return cache;
    }

    /**
     * @return the addedToAxis
     */
    public MetadataCache getAddedToAxis() {
        return addedToAxis;
    }

    /**
     * @return the indirectlyAdded
     */
    public MetadataCache getIndirectlyAdded() {
        return indirectlyAdded;
    }

    /**
     * @return the addedToFilter
     */
    public MetadataCache getAddedToFilter() {
        return addedToFilter;
    }

    private void recordNodeAddedToAxis(SimpleNode node, Metadata metadata) {
        getAddedToAxis().put(node.jjtGetValue().toString(), metadata);
    }
}
