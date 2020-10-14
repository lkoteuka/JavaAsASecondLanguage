## Java As A Second Language
### Lecture 05
### Annotations & Reflection

---
## Agenda

- Annotations
- Reflection
- Practice 1, 2, 3
- Live coding - Spring DI
- Into to Homework 3


---
## Annotations
#### What annotations did you see before?


---
## Override
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}
```

---
## Retention Policy
```java
// Describe the various policies for retaining annotations.  
// @since 1.5 
public enum RetentionPolicy {
    // Annotations are to be discarded by the compiler.
    SOURCE,
    // Annotations are to be recorded in the class file by the compiler
    // but need not be retained by the VM at run time.
    CLASS,  // <-- default
    // Annotations are to be recorded in the class file by the compiler and
    // retained by the VM at run time, so they may be read reflectively.
    RUNTIME
}
```


---
## Element Type
@see java.lang.annotation.ElementType


---
## Practice 1
### Reflection Playground

---
## Practice 2
### (De) serialization


---
## Dynamic Proxy
@see [Proxy Oracle docs](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/reflect/Proxy.html)

```java
InvocationHandler handler = new MyInvocationHandler(...);
Foo f = (Foo) Proxy.newProxyInstance(
          Foo.class.getClassLoader(),
          new Class`?`[] { Foo.class },
          handler
)
```


---
## Practice 3
### Dynamic Proxy


---
## Live coding
### Spring DI


---
## Homework 3
### Intro

---
## TIL
- Annotations
- Reflection
- Dynamic Proxy 
- Spring DI
- Homework 3 
