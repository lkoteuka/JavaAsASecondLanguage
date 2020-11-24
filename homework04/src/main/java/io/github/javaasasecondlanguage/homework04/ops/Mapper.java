package io.github.javaasasecondlanguage.homework04.ops;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;


public interface Mapper {
    void apply(Record inputRecord, Collector collector);
}
