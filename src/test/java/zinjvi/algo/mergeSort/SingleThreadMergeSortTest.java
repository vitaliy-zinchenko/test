package zinjvi.algo.mergeSort;

import org.junit.Ignore;

@Ignore
public class SingleThreadMergeSortTest extends AbstractMergeSortTest {

    @Override
    protected MergeSort createMergeSort() {
        return new SingleThreadMergeSort();
    }
}