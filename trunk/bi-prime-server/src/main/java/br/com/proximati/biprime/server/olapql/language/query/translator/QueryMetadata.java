/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

/**
 *
 * @author luiz
 */
public class QueryMetadata {
    private List<Node> axisNodeList = new ArrayList<Node>();
    private BidiMap<Node, String> axisNodeCoordinateMap = new DualHashBidiMap<Node, String>();
    private Map<Node, Metadata> axisNodeMetadataMap = new HashMap<Node, Metadata>();
    private ASTSelect select;

    public ASTSelect getSelect() {
        return select;
    }

    public void setSelect(ASTSelect select) {
        this.select = select;
    }

    public BidiMap<Node, String> getAxisNodeCoordinateMap() {
        return axisNodeCoordinateMap;
    }

    public void setAxisNodeCoordinateMap(BidiMap<Node, String> axisNodeCoordinateMap) {
        this.axisNodeCoordinateMap = axisNodeCoordinateMap;
    }

    public List<Node> getAxisNodeList() {
        return axisNodeList;
    }

    public void setAxisNodeList(List<Node> axisNodeList) {
        this.axisNodeList = axisNodeList;
    }

    public Map<Node, Metadata> getAxisNodeMetadataMap() {
        return axisNodeMetadataMap;
    }

    public void setAxisNodeMetadataMap(Map<Node, Metadata> axisNodeMetadataMap) {
        this.axisNodeMetadataMap = axisNodeMetadataMap;
    }
}
