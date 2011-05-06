/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.query.translator;

import br.com.proximati.biprime.metadata.entity.Cube;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Measure;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.metadata.entity.Property;
import java.util.HashMap;
import java.util.Map;

/**
 * Bolsa capaz de guardar um conjunto de instâncias de metadados,
 * cujos nomes podem ser utilizados para posterior recuperação.
 * 
 * @author luiz
 */
public class MetadataBag {

    private static final String CUBE_PREFIX = "C#";
    private static final String LEVEL_PREFIX = "L#";
    private static final String MEASURE_PREFIX = "M#";
    private static final String PROPERTY_PREFIX = "P#";
    private static final String FILTER_PREFIX = "F#";

    /**
     * Retorna um nível dado o seu nome.
     * @param name
     * @return
     */
    public Level getLevel(String name) {
        return (Level) getMetadata(LEVEL_PREFIX, name);
    }

    /**
     * Retorna uma métrica dado o seu nome.
     * @param name
     * @return
     */
    public Measure getMeasure(String name) {
        return (Measure) getMetadata(MEASURE_PREFIX, name);
    }

    /**
     * Returna um cubo dado o seu nome.
     * @param name
     * @return
     */
    public Cube getCube(String name) {
        return (Cube) getMetadata(CUBE_PREFIX, name);
    }

    /**
     * Retorna um filtro dado o seu nome.
     * @param name
     * @return
     */
    public Filter getFilter(String name) {
        return (Filter) getMetadata(FILTER_PREFIX, name);
    }

    public Property getProperty(String name) {
        return (Property) getMetadata(PROPERTY_PREFIX, name);
    }
    /**
     * Mapa que guarda as instâncias dos metadados.
     */
    private Map<String, Metadata> internalBag = new HashMap<String, Metadata>();

    /**
     * Retorna um metadado diretamente do maapa dado seu prefixo e o seu nome.
     * @param prefix
     * @param name
     * @return
     */
    private Metadata getMetadata(String prefix, String name) {
        StringBuilder key = new StringBuilder(prefix);
        key.append(name);
        return internalBag.get(key.toString());
    }

    /**
     * Coloca um metadado na bolsa.
     * @param metadata
     */
    public void put(String key, Metadata metadata) {
        internalBag.put(metadata.getClass().getSimpleName().charAt(0) + "#" + key, metadata);
    }

    /**
     * Coloca todos os metadados de uma segunda bolsa junto com os metadados
     * desta.
     * @param bag
     */
    public void put(MetadataBag bag) {
        internalBag.putAll(bag.internalBag);
    }

    /**
     * Retorna a quantidade de metadados na bolsa.
     * @return
     */
    public int size() {
        return internalBag.size();
    }

    public Map<String, Metadata> getInternalBag() {
        return internalBag;
    }
}
