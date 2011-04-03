/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bi.olapql.language.utils;

import br.com.bi.model.entity.metadata.Cube;
import br.com.bi.model.entity.metadata.Filter;
import br.com.bi.model.entity.metadata.Level;
import br.com.bi.model.entity.metadata.Measure;
import br.com.bi.model.entity.metadata.Metadata;
import br.com.bi.model.entity.metadata.Property;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author luiz
 */
public class MetadataCache {
    
    private Map<String, Metadata> internalCache = new TreeMap<String, Metadata> ();
    private static String CUBE_PREFIX = "C#";
    private static String LEVEL_PREFIX = "L#";
    private static String MEASURE_PREFIX = "M#";
    private static String PROPERTY_PREFIX = "P#";
    private static String FILTER_PREFIX = "F#";
    
    public Map<String, Metadata> getInternalMap() {
        return internalCache;
    }

    public Level getLevel(String key) {
        return (Level) internalCache.get(LEVEL_PREFIX + key);
    }
    
    public Measure getMeasure(String key) {
        return (Measure) internalCache.get(MEASURE_PREFIX + key);
    }
    
    public Cube getCube(String key) {
        return (Cube) internalCache.get(CUBE_PREFIX + key);
    }
    
    public Filter getFilter(String key) {
        return (Filter) internalCache.get(FILTER_PREFIX + key);
    }
    
    public Property getProperty(String key) {
        return (Property) internalCache.get(PROPERTY_PREFIX + key);
    }
    
    public void put(String key, Metadata metadata) {
        internalCache.put(metadata.getClass().getSimpleName().charAt(0) + "#" + key, metadata);
    }
    
    public void put(MetadataCache cache) {
        internalCache.putAll(cache.getInternalMap());
    }
}
