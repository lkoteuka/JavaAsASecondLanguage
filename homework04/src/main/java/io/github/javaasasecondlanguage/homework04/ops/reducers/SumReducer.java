package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Map;

/**
 * Counts sum of values in a specified column for each group and returns a single record with a sum.
 */
public class SumReducer implements Reducer {
    private final String inputColumn;
    private final String outputColumn;
    private double sum;

    public SumReducer(String inputColumn, String outputColumn) {
//        throw new IllegalStateException("You must implement this");
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
        this.sum = 0.0;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
//        throw new IllegalStateException("You must implement this");
        sum += inputRecord.getDouble(this.inputColumn);
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
//        throw new IllegalStateException("You must implement this");
        Record tempRecord = new Record(groupByEntries);
        tempRecord.set(outputColumn, sum);
        collector.collect(tempRecord);
        sum = 0.0;
    }

}
