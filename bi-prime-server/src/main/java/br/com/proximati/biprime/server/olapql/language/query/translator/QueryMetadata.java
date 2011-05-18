/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.server.olapql.language.query.AbstractQueryVisitor;
import br.com.proximati.biprime.metadata.Application;
import br.com.proximati.biprime.metadata.entity.Cube;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.metadata.entity.Property;
import br.com.proximati.biprime.server.olapql.language.query.ASTCube;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTFilterExpression;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevel;
import br.com.proximati.biprime.server.olapql.language.query.ASTLevelOrMeasureOrFilter;
import br.com.proximati.biprime.server.olapql.language.query.ASTProperty;
import br.com.proximati.biprime.server.olapql.language.query.ASTPropertyNode;
import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/**
 * Classe responsável pela extração dos metadados referenciados
 * pelos nós da árvore abstrata.
 * 
 * @author luiz
 */
public class QueryMetadata extends AbstractQueryVisitor {

    /** referência para o cubo consultado */
    private Cube cube;
    /** holds metadata directly added to both rows and columns axis */
    private MetadataBag referencedByAxisNodes = new MetadataBag();
    /** holds metadata referenced by query filter */
    private MetadataBag referencedByFilter = new MetadataBag();
    /** mapa com os nós presentes nos eixos, cada um apontado para o metadado que ele referencia */
    private Map<SimpleNode, Metadata> axisNodeMetadataMap = new HashMap<SimpleNode, Metadata>();
    /** list used to put the nodes in the same order they appear in the query axis.
     * It's useful on translation of "group by" expression, due to its assortment. */
    private List<SimpleNode> axisNodeList = new ArrayList<SimpleNode>();

    public QueryMetadata(ASTSelect select) throws Exception {
        visit(select, null);
    }

    public QueryMetadata(ASTFilterExpression filterExpression) throws Exception {
        visit(filterExpression, null);
    }

    @Override
    public void visit(ASTCube node, Object data) throws Exception {
        cube = Application.getCubeDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
    }

    @Override
    public void visit(ASTLevelOrMeasureOrFilter node, Object data) throws Exception {
        Metadata metadata = Application.getMeasureDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));

        if (metadata == null) {
            metadata = Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
            if (metadata == null) {
                metadata = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
                QueryParser parser = new QueryParser(IOUtils.toInputStream(((Filter) metadata).getExpression()));
                visit(parser.detachedFilterExpression(), data);
            }
        }

        referencedByAxisNodes.put(node.jjtGetValue().toString(), metadata);
        axisNodeMetadataMap.put(node, metadata);
        axisNodeList.add(node);

        super.visit(node, data);
    }

    @Override
    public void visit(ASTPropertyNode node, Object data) throws Exception {
        String[] str = node.jjtGetValue().toString().split("\\.");
        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));
        Property property = level.getProperty(TranslationUtils.extractName(str[1]));
        referencedByAxisNodes.put(node.jjtGetValue().toString(), property);
        axisNodeMetadataMap.put(node, property);
        axisNodeList.add(node);
        super.visit(node, data);
    }

    @Override
    public void visit(ASTProperty node, Object data) throws Exception {
        String[] str = node.jjtGetValue().toString().split("\\.");
        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(str[0]));
        Property property = level.getProperty(TranslationUtils.extractName(str[1]));
        referencedByFilter.put(node.jjtGetValue().toString(), property);
    }

    @Override
    public void visit(ASTLevel node, Object data) throws Exception {
        Level level = Application.getLevelDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
        if (level != null)
            referencedByFilter.put(node.jjtGetValue().toString(), level);
    }

    @Override
    public void visit(ASTFilter node, Object data) throws Exception {
        Filter filter = Application.getFilterDao().findByName(TranslationUtils.extractName(node.jjtGetValue().toString()));
        if (filter != null) {
            referencedByFilter.put(node.jjtGetValue().toString(), filter);
            QueryParser parser = new QueryParser(IOUtils.toInputStream(filter.getExpression()));
            visit(parser.detachedFilterExpression(), data);
        }
    }

    /**
     * Retorna a união de todos os metadados referenciados.
     * @return
     */
    public MetadataBag getAllReferencedMetadata() {
        MetadataBag bag = new MetadataBag();
        bag.put(referencedByAxisNodes);
        bag.put(referencedByFilter);
        return bag;
    }

    /**
     * Retorna os metadados que foram diretamente associados
     * aos nós dos eixos da consulta.
     * @return
     */
    public MetadataBag getMetadataReferencedByAxisNodes() {
        return referencedByAxisNodes;
    }

    /**
     * Retorna os metadados que foram referenciados pela
     * expressão de filtro da consulta.
     * @return
     */
    public MetadataBag getMetadataReferencedByFilter() {
        return referencedByFilter;
    }

    /**
     * Retorna referência para o cubo referenciado pela consulta.
     * @return
     */
    public Cube getCube() {
        return cube;
    }

    /**
     * Retorna o metadado referenciado pelo nó fornecido como parâmetro.
     * @param node
     * @return
     */
    public Metadata getMetadataReferencedBy(SimpleNode node) {
        return axisNodeMetadataMap.get(node);
    }

    /**
     * Retorna a lista de nós que aparecem nos eixos da consulta
     * na ordem de suas posições na árvore abstrata.
     * @return
     */
    public List<SimpleNode> getAxisNodeList() {
        return axisNodeList;
    }
}
