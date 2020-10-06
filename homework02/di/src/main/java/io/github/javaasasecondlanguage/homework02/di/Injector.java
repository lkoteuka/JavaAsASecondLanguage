package io.github.javaasasecondlanguage.homework02.di;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.stream.Collectors;

public class Injector {
    public static <T> T inject(Class<T> clazz) {
        return inject(clazz, null);
    }

    public static <T> T inject(Class<T> clazz, String qualifier) {
        T parameter = getObject(clazz, qualifier);
        if (parameter != null) {
            return parameter;
        }
        if (!clazz.isInterface()) {
            return null;
        } else {
            return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                new InvocationHandler() {
                    Object wrapped = null;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        if (wrapped == null) {
                            wrapped = getObject(clazz, qualifier);
                        }
                        return method.invoke(wrapped, args);
                    }
                }
            );
        }
    }

    public static <T> T getObject(Class<T> clazz, String qualifier) {
        T parameter = null;
        List<Object> qualifiedParameters;
        if (qualifier != null) {
            qualifiedParameters = Context.getParameters().get(qualifier);
        } else {
            qualifiedParameters = Context.getParameters().values()
                    .stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
        if (qualifiedParameters != null) {
            for (Object obj : qualifiedParameters) {
                if (obj.getClass().equals(clazz)) {
                    parameter = (T) obj;
                    break;
                }
            }
            if (parameter == null) {
                for (Object obj : qualifiedParameters) {
                    if (clazz.isInstance(obj)) {
                        parameter = (T) obj;
                        break;
                    }
                }
            }
        }
        return parameter;
    }
}
