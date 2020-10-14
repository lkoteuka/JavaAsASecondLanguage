package io.github.javaasasecondlanguage.lecture05;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class SerializationUtilTest {

    static class A {
        private Integer anInteger;
        private String aString;

        public Integer getAnInteger() {
            return anInteger;
        }

        public String getaString() {
            return aString;
        }
    }

    static class B {
        private double aDouble;
        private ArrayList<String> list;

        public double getaDouble() {
            return aDouble;
        }

        public ArrayList<String> getList() {
            return list;
        }
    }

    @Test
    void serializeTest() {
        A a = new A();
        a.anInteger = 42;
        a.aString = "xxx";

        var result = SerializationUtil.serialize(a);
        assertEquals(2, result.size());
        assertEquals(42, result.get("anInteger"));
        assertEquals("xxx", result.get("aString"));
        System.out.println(result);
    }

    @Test
    void deserializeTest() {
        Map<String, ?> map = Map.of("anInteger", 42, "aString", "xxx");
        var result = SerializationUtil.deserialize(map, A.class);

        assertEquals(A.class, result.getClass());
        assertEquals(Integer.valueOf(42), result.getAnInteger());
        assertEquals("xxx", result.getaString());
    }

    @Test
    void serializeThenDeserialize() {
        var b = new B();
        b.aDouble = 1.2345;
        b.list = new ArrayList<>();
        b.list.addAll(List.of("1", "2", "3"));

        var newB = SerializationUtil.deserialize(SerializationUtil.serialize(b), B.class);

        assertEquals(b.getaDouble(), newB.getaDouble());
        assertEquals(b.getList(), newB.getList());
    }
}