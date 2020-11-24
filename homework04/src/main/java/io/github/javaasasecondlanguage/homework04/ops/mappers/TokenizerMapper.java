package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.List;

import static java.util.List.of;

/**
 * Splits text in the specified column into words, then creates a new record with each word.
 *
 * Split should happen on the following symbols: " ", ".", ",", "!", ";", "?", "'", ":"
 */
public class TokenizerMapper implements Mapper {
    private final String inputColumn;
    private final String outputColumn;
    private static final String SPLIT_PATTERN = "[\\s,\\.\\!\\;\\?\\'\\:\"]+";

    public TokenizerMapper(String inputColumn, String outputColumn) {
//        throw new IllegalStateException("You must implement this");
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
//        throw new IllegalStateException("You must implement this");
        String text = inputRecord.getString(this.inputColumn);
        for (String token: text.split(SPLIT_PATTERN)) {
            Record tmpRecord = inputRecord.copyColumnsExcept(List.of(this.inputColumn));
            tmpRecord.set(this.outputColumn, token);
            collector.collect(tmpRecord);
        }

    }
}
