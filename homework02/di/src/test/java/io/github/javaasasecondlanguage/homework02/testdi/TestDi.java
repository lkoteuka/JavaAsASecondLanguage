package io.github.javaasasecondlanguage.homework02.testdi;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestDi {
    @Test
    void shouldInjectInObjectFromContext() {
        var context = new Context();
        Context.getParameters().clear();
        context.register("value", "qualifier");
        var injected = Injector.inject(String.class, "qualifier");
        assertEquals("value", injected);
    }

    @Test
    void shouldRegisterAndInject() {
        var context = new Context();
        Context.getParameters().clear();
        var registeredValue = "value";
        context.register(registeredValue);
        var injected = Injector.inject(String.class);
        assertEquals(registeredValue, injected);
    }

    @Test
    void shouldRegisterWithQualifierAndInjectWithoutQualifier() {
        var context = new Context();
        Context.getParameters().clear();
        var registeredValue = UUID.randomUUID();
        var qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        var injected = Injector.inject(UUID.class, qualifier);
        assertEquals(registeredValue, injected);
    }

    @Test
    void shouldInjectSubclassWithoutQualifierWithRegistrationWithQualifier() {
        var context = new Context();
        Context.getParameters().clear();
        Integer registeredValue = 42;
        var qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        var injected = Injector.inject(Number.class);
        assertEquals(registeredValue, injected);
    }

    @Test
    void shouldSuccessMultipleInjection() {
        var context = new Context();
        Context.getParameters().clear();
        var registeredValue2 = "value";
        UUID registeredValue3 = UUID.randomUUID();
        context.register(registeredValue2);
        context.register(registeredValue3);
        assertEquals(registeredValue2, Injector.inject(String.class));
        assertEquals(registeredValue3, Injector.inject(UUID.class));
    }

    @Test
    void shouldInjectWithoutRegistration() {
        Context.getParameters().clear();
        var injected = Injector.inject(TestInterface.class);
        assertNotNull(injected);
        assertThrows(NullPointerException.class, injected::method);
    }

    @Test
    void shouldInjectWithQualifierWithoutRegistration() {
        Context.getParameters().clear();
        var qualifier = "qualifier";
        var injected = Injector.inject(TestInterface.class, qualifier);
        assertNotNull(injected);
        assertThrows(NullPointerException.class, injected::method);
    }

    @Test
    void shouldSuccessSubclassInjection() {
        var context = new Context();
        Context.getParameters().clear();
        Integer intValue = 42;
        context.register(intValue);
        var injected = Injector.inject(Number.class);
        assertEquals(injected, injected);
    }

    @Test
    void shouldSubclassInjectionWithQualifier() {
        var context = new Context();
        Context.getParameters().clear();
        Integer registeredValue = 42;
        var qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        var injected = Injector.inject(Number.class, qualifier);
        assertEquals(registeredValue, injected);
    }

    interface TestInterface {
        int method();
    }
}