package io.github.javaasasecondlanguage.lecture05.practice1;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@Disabled
public class ReflectionPlayground {
    @Test
    void createA() {
        // create A instance using reflection
        // hint: A.class
    }

    @Test
    void createB() {
        // create B instance using reflection
        // hint: change access
    }

    @Test
    void listMethodsA() {
        // list methods of class A
    }

    @Test
    void listMethodsAnnotatedWithOverride() {
        // list Methods of class C annotated with override
    }

    static class A {
        String string;
        Integer integer;
    }

    static class B {
        String string;
        Integer integer;

        private B() {
        }
    }

    static class C {
        String string;
        Integer integer;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            C c = (C) o;
            return Objects.equals(string, c.string)
                   && Objects.equals(integer, c.integer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string, integer);
        }
    }
}
