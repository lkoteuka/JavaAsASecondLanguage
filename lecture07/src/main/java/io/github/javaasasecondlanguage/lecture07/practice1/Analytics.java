package io.github.javaasasecondlanguage.lecture07.practice1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.logging.Logger;

public class Analytics {
    private static final int MAX_SENT_ANALYSIS = 10;
    private static final int MAX_DOWNLOAD_HACK_CONTENT = 2;
    private static final int QUEUE_REQUEST_SIZE = 100;
    private volatile Integer maxContentId = 2500000;
    private final AtomicInteger currentContentId = new AtomicInteger(2400000);
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(QUEUE_REQUEST_SIZE);
    private final ConcurrentHashMap<String, Stats> stats = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this term
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms) {
        for (String term : terms) {
            stats.put(term.toLowerCase(), new Stats());
        }
        ExecutorService es =
                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        es.submit(this::updateLatestContentId);
        for (int i = 0; i < MAX_DOWNLOAD_HACK_CONTENT; i++) {
            es.submit(this::downloadHackerNewsContent);
        }
        for (int i = 0; i < MAX_SENT_ANALYSIS; i++) {
            es.submit(this::getSentiments);
        }
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            logger.info("Exception in 'analyzeHackerNews' function: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void downloadHackerNewsContent() {
        while (true) {
            int current = currentContentId.getAndIncrement();
            if (current < maxContentId) {
                try {
                    String content = HackerNewsClient.getContent(current);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode root = objectMapper.readTree(content);
                    JsonNode textNode = root.get("text");
                    if (textNode != null) {
                        String text = textNode.asText();

                        for (String sentence : text.split("\\.")) {
                            if (sentence != null && !sentence.isEmpty()) {
                                try {
                                    queue.put(sentence);
                                } catch (InterruptedException e) {
                                    var mess = e.getMessage();
                                    logger.info(
                                            "Exception in 'downloadHackerNewsContent' function: "
                                                    + mess);
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    logger.info(
                            "Exception in 'downloadHackerNewsContent' function: "
                                    + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private Object getSentiments() throws InterruptedException {
        while (true) {
            String text = queue.take();
            String sentiments = null;
            for (var word : stats.keySet()) {
                if (text.contains(word)) {
                    System.out.println("Found word: " + word);
                    try {
                        sentiments = SentimentsClient.sentiments(text);
                        break;
                    } catch (Exception e) {
                        logger.info("Exception in 'getSentiments' function: " + e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                }
            }
            if (sentiments == null) {
                continue;
            }
            text = text.toLowerCase();
            var sentences = text.split("[.!?]");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode response = null;
            try {
                response = objectMapper.readTree(sentiments);
            } catch (JsonProcessingException e) {
                logger.info("Exception in 'getSentiments' function: " + e.getMessage());
                e.printStackTrace();
                continue;
            }
            JsonNode sentimentsList = response.get("output");
            if (sentences.length == sentimentsList.size()) {
                for (int i = 0; i < sentences.length; i++) {
                    for (Map.Entry<String, Stats> s : stats.entrySet()) {
                        if (sentences[i].contains(s.getKey())) {
                            s.getValue().mentions.incrementAndGet();
                            String marker = sentimentsList.get(i).asText("");
                            if (marker.contains("Verynegative")) {
                                s.getValue().score.add(-3);
                            }
                            if (marker.contains("Negative")) {
                                s.getValue().score.add(-1);
                            }
                            if (marker.contains("Positive")) {
                                s.getValue().score.add(1);
                            }
                            if (marker.contains("Verypositive")) {
                                s.getValue().score.add(3);
                            }
                        }
                    }
                }
            }
        }
    }


    void updateLatestContentId() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.info("Exception in 'updateLatestContentId' function: " + e.getMessage());
                e.printStackTrace();
                return;
            }
            try {
                maxContentId = Integer.valueOf(HackerNewsClient.getLatestContentId());
            } catch (IOException e) {
                logger.info("Exception in 'updateLatestContentId' function: " + e.getMessage());
                e.printStackTrace();
                continue;
            }
            System.out.println("Max content id: " + maxContentId);
        }

    }

    public Map<String, Stats> getStats() {
        return stats;
    }

    public static class Stats {
        volatile AtomicInteger mentions = new AtomicInteger(0);
        volatile LongAdder score = new LongAdder();

        @Override
        public String toString() {
            return "Stats{"
                    + "mentions=" + mentions
                    + ", score=" + score
                    + ", rating=" + score.intValue() / (mentions.get() + 1.)
                    + "}\n";
        }
    }
}
