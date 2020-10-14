package io.github.javaasasecondlanguage.lecture05.practice3;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

public class ProxyUtil {
    static <T> List<T> wrap(List<T> original) {
        var handler = new Handler(original);
        var proxy = Proxy.newProxyInstance(
                List.class.getClassLoader(),
                new Class[]{List.class},
                handler
        );
        return (List<T>) proxy;
    }

    static <K, V> Map<K, V> wrap(Map<K, V> original) {
        throw new RuntimeException("Not implemented");
    }
}
