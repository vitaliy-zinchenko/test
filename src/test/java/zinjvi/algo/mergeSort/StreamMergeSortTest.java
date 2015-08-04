package zinjvi.algo.mergeSort;

import org.junit.Ignore;

@Ignore
public class StreamMergeSortTest extends AbstractMergeSortTest {

    @Override
    protected MergeSort createMergeSort() {
        return new StreamMergeSort();
    }
}