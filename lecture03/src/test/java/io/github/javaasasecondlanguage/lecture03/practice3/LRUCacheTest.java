package io.github.javaasasecondlanguage.lecture03.practice3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
class LRUCacheTest {
    @Test
    void lruCache1(){
        LRUCache cache = new LRUCache(1);
        cache.put("1","1");
        assertEquals(1, cache.size());
        cache.put("2","2");
        assertEquals(1, cache.size());
        assertEquals("2", cache.get("2"));
        assertNull(cache.get("1"));
    }
    
    @Test
    void lruCache2(){
        LRUCache cache = new LRUCache(2);
        cache.put("1","1");
        assertEquals(1, cache.size());
        cache.put("2","2");
        assertEquals(2, cache.size());
        cache.put("3","3");
        assertEquals(2, cache.size());
        assertEquals("2", cache.get("2"));
        assertEquals("3", cache.get("3"));
        assertNull(cache.get("1"));
    }

    @Test
    void illegalCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new LRUCache(-1));
    }
}