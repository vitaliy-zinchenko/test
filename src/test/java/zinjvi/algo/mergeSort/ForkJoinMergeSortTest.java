package zinjvi.algo.mergeSort;

import org.junit.Ignore;

@Ignore
public class ForkJoinMergeSortTest extends AbstractMergeSortTest {
    @Override
    protected MergeSort createMergeSort() {
        return new ForkJoinMergeSort();
    }
}
