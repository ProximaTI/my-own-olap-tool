/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.proximati.biprime.server.olapql.language.utils;

import br.com.proximati.biprime.metadata.entity.Cube;
import br.com.proximati.biprime.metadata.entity.Filter;
import br.com.proximati.biprime.metadata.entity.Level;
import br.com.proximati.biprime.metadata.entity.Measure;
import br.com.proximati.biprime.metadata.entity.Metadata;
import br.com.proximati.biprime.metadata.entity.Property;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author luiz
 */
public class MetadataBag {
    
    private Map<String, Metadata> internalBag = new TreeMap<String, Metadata> ();
    private static String CUBE_PREFIX = "C#";
    private static String LEVEL_PREFIX = "L#";
    private static String MEASURE_PREFIX = "M#";
    private static String PROPERTY_PREFIX = "P#";
    private static String FILTER_PREFIX = "F#";
    
    public Map<String, Metadata> getInternalMap() {
        return internalBag;
    }

    public Level getLevel(String key) {
        return (Level) internalBag.get(LEVEL_PREFIX + key);
    }
    
    public Measure getMeasure(String key) {
        return (Measure) internalBag.get(MEASURE_PREFIX + key);
    }
    
    public Cube getCube(String key) {
        return (Cube) internalBag.get(CUBE_PREFIX + key);
    }
    
    public Filter getFilter(String key) {
        return (Filter) internalBag.get(FILTER_PREFIX + key);
    }
    
    public Property getProperty(String key) {
        return (Property) internalBag.get(PROPERTY_PREFIX + key);
    }
    
    public void put(String key, Metadata metadata) {
        internalBag.put(metadata.getClass().getSimpleName().charAt(0) + "#" + key, metadata);
    }
    
    public void put(MetadataBag bag) {
        internalBag.putAll(bag.getInternalMap());
    }
}
