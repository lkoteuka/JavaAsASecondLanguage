package io.github.javaasasecondlanguage.lecture05.practice2;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class SerializationUtilTest {

    static class A {
        private Integer theInteger;
        private String theString;

        public Integer getTheInteger() {
            return theInteger;
        }

        public String getTheString() {
            return theString;
        }
    }

    static class B {
        private double theDouble;
        private ArrayList<String> list;

        public double getTheDouble() {
            return theDouble;
        }

        public ArrayList<String> getList() {
            return list;
        }
    }

    @Test
    void serializeTest() {
        A a = new A();
        a.theInteger = 42;
        a.theString = "xxx";

        var result = SerializationUtil.serialize(a);
        assertEquals(2, result.size());
        assertEquals(42, result.get("theInteger"));
        assertEquals("xxx", result.get("theString"));
    }

    @Test
    void deserializeTest() {
        Map<String, ?> map = Map.of("theInteger", 42, "theString", "xxx");
        var result = SerializationUtil.deserialize(map, A.class);

        assertEquals(A.class, result.getClass());
        assertEquals(Integer.valueOf(42), result.getTheInteger());
        assertEquals("xxx", result.getTheString());
    }

    @Test
    void serializeThenDeserialize() {
        var b = new B();
        b.theDouble = 1.2345;
        b.list = new ArrayList<>();
        b.list.addAll(List.of("1", "2", "3"));

        var newB = SerializationUtil.deserialize(SerializationUtil.serialize(b), B.class);

        assertEquals(b.getTheDouble(), newB.getTheDouble());
        assertEquals(b.getList(), newB.getList());
    }
}