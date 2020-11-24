package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

/**
 * Shifts selected column to lowercase.
 */
public class LowerCaseMapper implements Mapper {
    private final String column;

    public LowerCaseMapper(String column) {
//        throw new IllegalStateException("You must implement this");
        this.column = column;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
//        throw new IllegalStateException("You must implement this");
        Record temporaryRecord = inputRecord.copy();
        String temporaryString = temporaryRecord.getString(this.column).toLowerCase();
        temporaryRecord.set(this.column, temporaryString);
        collector.collect(temporaryRecord);
    }
}
