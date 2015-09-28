package zinjvi.algo.sort;


import java.util.Comparator;

/**
 * Created by Vitaliy on 8/15/2015.
 */
public class BubbleSorter<T> implements Sorter<T> {
    @Override
    public void sort(T[] arr, Comparator<T> comparator) {
        for (int sortableSize = arr.length; sortableSize > 0 ; sortableSize--) {
            for (int i = 1; i < sortableSize; i++) {
                if ( comparator.compare( arr[i-1], arr[i] ) < 0 ) {
                    T rightValue = arr[i];
                    arr[i] = arr[i-1];
                    arr[i-1] = rightValue;
                }
            }
        }
    }
}
