package zinjvi.algo.sort;

import java.util.concurrent.Executors;

/**
 * Created by Vitaliy on 8/17/2015.
 */
public class BubbleForkConcurrencySorterTest extends SorterTest {
    @Override
    protected Sorter<Integer> createSorter() {
        return new BubbleForkConcurrencySorter<>(Executors.newFixedThreadPool(4));
    }
}
