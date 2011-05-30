/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.SimpleNode;
import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

/**
 *
 * @author luiz
 */
public class TranslationContext {

    private ASTSelect translatedSelect;
    /** encapsula os metadados referenciados pelos nó da árvore abstrata da consulta. */
    private QueryMetadata queryMetadata;
    /** saída onde a tradução é escrita. */
    private StringBuilder output = new StringBuilder();
    /** mapa com a posição de cada nó nos eixos da consulta. */
    private BidiMap<SimpleNode, String> axisNodePositionsMap = new DualHashBidiMap<SimpleNode, String>();

    /**
     *
     * @param queryMetadata
     */
    public TranslationContext(QueryMetadata queryMetadata) {
        this.queryMetadata = queryMetadata;
    }

    /**
     * 
     * @return
     */
    public BidiMap<SimpleNode, String> getAxisNodePositionsMap() {
        return axisNodePositionsMap;
    }

    /**
     * @return the queryMetadata
     */
    public QueryMetadata getQueryMetadata() {
        return queryMetadata;
    }

    /**
     * @return the output
     */
    public StringBuilder getOutput() {
        return output;
    }

    public ASTSelect getTranslatedSelect() {
        return translatedSelect;
    }

    void setTranslatedSelect(ASTSelect translatedSelect) {
        this.translatedSelect = translatedSelect;
    }
}
