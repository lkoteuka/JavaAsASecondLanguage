package io.github.javaasasecondlanguage.lecture05.practice3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProxyUtilTest {
    @Test
    void listProxyUtilTest() {
        List<String> strings = ProxyUtil.wrap(List.of("a", "b", "c"));
        // should show size
        assertFalse(strings.contains("x"));
    }

    // Create a handler to show map history after
    // each put/remove operation
    // Example:
    //
    //  map.add("x", 123)
    //  < History:
    //  < []
    //  < [("x", 123)]
    //
    //
    //  map.add("y", 42)
    //  < History:
    //  < []
    //  < [("x", 123)]
    //  < [("x", 123), ("y", 42)]
    //
    //  map.remove("y")
    //  < History:
    //  < []
    //  < [("x", 123)]
    //  < [("x", 123), ("y", 42)]
    //  < [("x", 123)]
    @Test
    void mapModificationHistory() {
        Map<String, Integer> original = new HashMap<>();
        var proxy = ProxyUtil.wrap(original);
        proxy.put("x", 123);
        proxy.put("y", 42);
        proxy.remove("y");
        assertEquals(1, proxy.size());
    }
}