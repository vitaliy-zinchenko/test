package zinjvi.algo.sort;

import java.util.concurrent.Executors;

/**
 * Created by Vitaliy on 8/17/2015.
 */
public class BubbleConcurrencySorterTest extends SorterTest {
    @Override
    protected Sorter<Integer> createSorter() {
        return new BubbleConcurrencySorter<>(Executors.newFixedThreadPool(4));
    }
}
