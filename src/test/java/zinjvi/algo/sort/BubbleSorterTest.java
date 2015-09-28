package zinjvi.algo.sort;

/**
 * Created by Vitaliy on 8/17/2015.
 */
public class BubbleSorterTest extends SorterTest {
    @Override
    protected Sorter<Integer> createSorter() {
        return new BubbleSorter<>();
    }
}
