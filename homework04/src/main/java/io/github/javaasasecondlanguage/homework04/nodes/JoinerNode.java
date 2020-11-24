package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinerNode implements ProcNode {
    private final int gateNumbers = 2;
    private final List<String> keyColumns;
    private final List<Record> left = new ArrayList<>();
    private final List<Record> right = new ArrayList<>();
    private final RoutingCollector collector = new RoutingCollector();
    private final Boolean[] terminated;

    public JoinerNode(List<String> keyColumns) {
        this.terminated = new Boolean[gateNumbers];
        Arrays.fill(this.terminated, false);
        this.keyColumns = keyColumns;
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber == 0) {
            if (inputRecord.isTerminal()) {
                terminated[gateNumber] = true;
            }
            else {
                left.add(inputRecord);
            }
        }
        else if (gateNumber == 1){
            if (inputRecord.isTerminal()) {
                terminated[gateNumber] = true;
            }
            else {
                right.add(inputRecord);
            }
        }
        else {
            throw new IllegalArgumentException("Gate does not exist: " + gateNumber);
        }
        if (terminated[0] && terminated[1]) {
            for (Record firstRecord : left) {
                for (Record secondRecord : right) {
                    if (firstRecord.copyColumns(keyColumns).equals(secondRecord.copyColumns(keyColumns)))
                        getCollector().collect(new Record(firstRecord.getData()).setAll(secondRecord.getData()));
                }
            }
            collector.collect(Record.terminalRecord());
            terminated[0] = false;
            terminated[1] = false;
            left.clear();
            right.clear();
        }
    }
}
