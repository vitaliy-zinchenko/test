package zinjvi.algo.sort;

import java.util.Comparator;

/**
 * Created by Vitaliy on 8/15/2015.
 */
public interface Sorter<T> {
    void sort(T[] arr, Comparator<T> comparator);
}
