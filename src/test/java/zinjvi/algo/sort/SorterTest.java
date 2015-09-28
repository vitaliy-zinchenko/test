package zinjvi.algo.sort;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Vitaliy on 8/17/2015.
 */
public abstract class SorterTest {

    private Sorter<Integer> sorter;

    protected abstract Sorter<Integer> createSorter();

    @Before
    public void setUp() throws Exception {
        sorter = createSorter();
    }

    @Test
         public void testSort() throws Exception {
        // given
        Integer[] array = new Integer[]{7, 9, 7, 0, 6, 0, 4, 2, 0, 3}; // 7, 9, 7, 0, 6, 0, 4, 2, 0, 3

        // when
        sorter.sort(array, (item1, item2) -> item2 - item1);

        // then
        Integer[] expectedResult = new Integer[]{0, 0, 0, 2, 3, 4, 6, 7, 7, 9};
        System.out.println("array=" + Arrays.toString(array));
        Assert.assertArrayEquals(expectedResult, array);
    }

    @Test
    public void testSortPerformance() throws Exception {
        // given
        int size = 10000;
        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }

//        System.out.println(Arrays.toString(array));

        // when
        Stopwatch stopwatch = Stopwatch.createStarted();
        sorter.sort(array, (item1, item2) -> item2 - item1);
        stopwatch.stop();
        System.out.println("Time = " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        System.out.println("lock time MICROSECONDS = " + BubbleConcurrencySorter.ATOMIC_LONG);
        System.out.println("lock time MILLISECONDS = " + TimeUnit.MICROSECONDS.toMillis(BubbleConcurrencySorter.ATOMIC_LONG.get()));

        // then
//        System.out.println(Arrays.toString(array));
    }

}