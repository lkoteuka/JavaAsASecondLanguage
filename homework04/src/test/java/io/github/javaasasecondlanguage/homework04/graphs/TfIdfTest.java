package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TfIdfTest {
    private ProcNode inputNode;
    private ProcNode outputNode;

    @BeforeEach
    void setUp() {
        var graph = TfIdf.createGraph();

        assertNotNull(graph.getInputNodes());
        assertNotNull(graph.getInputNodes());

        assertEquals(1, graph.getInputNodes().size());
        assertEquals(1, graph.getOutputNodes().size());

        inputNode = graph.getInputNodes().get(0);
        outputNode = graph.getOutputNodes().get(0);
    }

    @Test
    void general() {
        var listDumper = new ListDumper();
        GraphPartBuilder
                .startFrom(outputNode)
                .map(listDumper);

        for (var record : inputRecords) {
            inputNode.push(record, 0);
        }
        inputNode.push(Record.terminalRecord(), 0);

        List<Record> actualRecords = listDumper.getRecords();
        System.out.println(actualRecords);
        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"Id", "Author", "Text"},
            new Object[][]{
                    {1, "My", "In some day"},
                    {2, "Favorite", "This monday was beautiful day"},
                    {3, "Life", "This or not this"},
                    {4, "Best", "What monday was not day"},
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Token", "TfIdf"},
            new Object[][]{
                    {1, "day", 0.096},
                    {1, "in", 0.462},
                    {1, "some", 0.462},
                    {2, "beautiful", 0.277},
                    {2, "day", 0.058},
                    {2, "monday", 0.139},
                    {2, "this", 0.139},
                    {2, "was", 0.139},
                    {3, "not", 0.173},
                    {3, "or", 0.347},
                    {3, "this", 0.347},
                    {4, "day", 0.058},
                    {4, "monday", 0.139},
                    {4, "not", 0.139},
                    {4, "was", 0.139},
                    {4, "what", 0.277},
            }
    );
}
