package zinjvi.algo.mergeSort;

import java.util.Arrays;

public class StreamMergeSort implements MergeSort {
    @Override
    public int[] sort(int[] array) {
        return Arrays.stream(array).sorted().toArray();
    }
}
