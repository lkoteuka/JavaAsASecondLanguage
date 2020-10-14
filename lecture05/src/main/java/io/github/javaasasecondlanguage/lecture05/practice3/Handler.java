package io.github.javaasasecondlanguage.lecture05.practice3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Handler implements InvocationHandler {
    private final List<?> original;

    public Handler(List<?> original) {
        this.original = original;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        System.out.println("Size: " + this.original.size());
        return method.invoke(original, args);
    }
}