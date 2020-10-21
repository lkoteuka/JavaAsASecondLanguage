package io.github.javaasasecondlanguage.lecture05.practice2;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Serialize an [instance] with a specification:
 * 1. [instance] has a default (no-arg) constructor
 * 2. [instance] super class is Object
 * 3. [instance] use concrete classes (not interfaces) as fields
 *
 * Bonus:
 * Serialize Collections and nested objects as nested Maps
 */
public class SerializationUtil {

    /**
     *
     * Serialize each field of [obj] as an entry in HashMap
     * class A {
     *     Integer i;
     * }
     *
     * var a = new A(); a.i = 42   ->   Map.of("i", 42)
     *
     * @param obj - object to serialize
     *
     * @return
     */
    static Map<String, ?> serialize(Object obj) {
        var result = new HashMap<String, Object>();
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                result.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *
     * @param obj - map representation of object
     * @param clazz - target type of deserialization
     */
    static <T> T deserialize(Map<String, ?> obj, Class<T> clazz) {
        T instance = null;
        try {
            var ctor = clazz.getDeclaredConstructor();
            instance = ctor.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, obj.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }
}