package io.github.javaasasecondlanguage.homework04.ops.reducers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Calculate frequency of values in column for each group.
 */
public class WordFrequencyReducer implements Reducer {
    private final String termColumn;
    private final String outputColumn;
    private final Map<String, Integer> freqDict = new HashMap<>();

    public WordFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, Collector collector, Map<String, Object> groupByEntries) {
        String tempText = inputRecord.getString(termColumn);
        freqDict.put(tempText, freqDict.getOrDefault(tempText, 0) + 1);
    }

    @Override
    public void signalGroupWasFinished(Collector collector, Map<String, Object> groupByEntries) {
        Integer allTerms = freqDict.values().stream().reduce(0, Integer::sum);
        freqDict.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList())
                .forEach(item -> {
            Record tempRecord = new Record(groupByEntries);
            tempRecord.set(this.termColumn, item.getKey());
            tempRecord.set(this.outputColumn, Double.valueOf(item.getValue()) / allTerms);
            collector.collect(tempRecord);
        });
        freqDict.clear();
    }

}
