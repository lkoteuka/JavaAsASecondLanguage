package io.github.javaasasecondlanguage.homework02.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Context {

    private static final Map<String, List<Object>> parameters = new HashMap<String, List<Object>>();

    public <T> Context register(T object, String qualifier) {
        parameters.compute(qualifier, (key, val) -> {
            if (val == null) {
                val = new ArrayList<Object>();
            }
            val.add(object);
            return val;
        });
        return this;
    }

    public <T> Context register(T object) {
        return register(object, null);
    }

    public static Map<String, List<Object>> getParameters() {
        return parameters;
    }

    public Object find(Class<?> clazz, String qualifier) {
        var found = parameters.get(qualifier).stream()
                .filter(clazz::isInstance)
                .collect(Collectors.toList());
        var foundSize = found.size();
        if (foundSize > 1) {
            throw new RuntimeException("Found more than 1 satisfying dependency!");
        }
        if (foundSize < 1) {
            throw new RuntimeException("Failed to find satisfying dependency!");
        }
        return found.get(0);
    }
}