package io.github.javaasasecondlanguage.homework01.mergeintervals;

import java.util.*;

public class IntervalsMerger {
    /**
     * Given array of intervals, merge overlapping intervals and sort them by start in
     * ascending order
     * Interval is defined as [start, end] where start < end
     * <p>
     * Examples:
     * [[1,3][2,4][5,6]] -> [[1,4][5,6]]
     * [[1,2][2,3]] -> [[1,3]]
     * [[1,4][2,3]] -> [[1,4]]
     * [[5,6][1,2]] -> [[1,2][5,6]]
     *
     * @param intervals is a nullable array of pairs [start, end]
     * @return merged intervals
     * @throws IllegalArgumentException if intervals is null
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null) {
            throw new IllegalArgumentException();
        }
        if (intervals.length == 0) {
            return new int[][]{};
        }
        Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
        for (int i = 0; i < intervals.length; i++) {
            treeMap.put(intervals[i][0], treeMap.getOrDefault(intervals[i][0], 0) + 1);
            treeMap.put(intervals[i][1], treeMap.getOrDefault(intervals[i][1], 0) - 1);
        }
        List entryList = new ArrayList(treeMap.entrySet());

        Integer counter = 0;
        for (Object obj : entryList) {
            counter += (Integer) ((Map.Entry) obj).getValue();
            ((Map.Entry) obj).setValue(counter);
        }
        List<Integer> answ = new ArrayList<Integer>();
        answ.add((Integer) ((Map.Entry) (entryList.get(0))).getKey());

        for (int i = 0; i < entryList.size(); i++) {
            if ((Integer) ((Map.Entry) entryList.get(i)).getValue() == 0) {
                answ.add((Integer) ((Map.Entry) (entryList.get(i))).getKey());
                if (i + 1 < entryList.size()) {
                    answ.add((Integer) ((Map.Entry) (entryList.get(i + 1))).getKey());
                }
            }
        }
        int[][] answRes = new int[answ.size() / 2][2];
        int j = 0;
        for (int i = 0; i < answ.size(); i += 2) {
            answRes[j][0] = answ.get(i);
            answRes[j][1] = answ.get(i + 1);
            j++;
        }
        return answRes;
    }
}
