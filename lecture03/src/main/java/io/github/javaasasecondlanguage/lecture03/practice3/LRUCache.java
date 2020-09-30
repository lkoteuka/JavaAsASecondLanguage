package io.github.javaasasecondlanguage.lecture03.practice3;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Cache that only stores 'capacity' values that were put last
 */
public class LRUCache extends LinkedHashMap<String, String>{
    private Integer capacity;

    public LRUCache(int capacity) {
        if (capacity < 0){
            throw new IllegalArgumentException("Not implemented");
        }
        this.capacity = capacity;
//        throw new RuntimeException("Not implemented");
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> m){
        if (this.size() > capacity){
            return true;
        }
        return false;
    }
}
