package zinjvi.algo.mergeSort;

import com.google.common.base.Stopwatch;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public abstract class AbstractMergeSortTest {

    MergeSort mergeSort;

    @Before
    public void setUp() throws Exception {
        mergeSort = createMergeSort();
    }

    @After
    public void tearDown() throws Exception {

    }

    protected abstract MergeSort createMergeSort();

    @Test
    public void testSort() throws Exception {
        // given
        int arraySize = 10_000_000;
        int[] src = generateSrc(arraySize);

        // when
        Stopwatch stopwatch = Stopwatch.createStarted();
        int[] sorted = mergeSort.sort(src);
        System.out.println("Elapsed: " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));

        // then
        int[] expected = generateExpected(arraySize);
        Assert.assertArrayEquals(expected, sorted);
    }

    private int[] generateSrc(int size) {
        int[] src = new int[size];
        int value = size - 1;
        for (int i = 0; i < size; i++) {
            src[i] = value--;
        }
        return src;
    }

    private int[] generateExpected(int size) {
        int[] src = new int[size];
        for (int i = 0; i < size; i++) {
            src[i] = i;
        }
        return src;
    }

}