## Java As A Second Language
### Lecture 05
### Annotations & Reflection

---
## Agenda

- Annotations
- Reflection
- Practice 1
- Spring DI
- Practice 2
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
## 
---
## TIL
- a
- b
- c
