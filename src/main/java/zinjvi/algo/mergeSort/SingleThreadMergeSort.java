package zinjvi.algo.mergeSort;

public class SingleThreadMergeSort extends AbstractMergeSort implements MergeSort {
    @Override
    public int[] sort(int[] src) {
        validate(src);
        if(canProcess(src)) {
            return src;
        }

        int[] leftArray = getLeftArray(src);

        leftArray = sort(leftArray);
        int[] rightArray = sort(getRightArray(src, leftArray));

        return merge(leftArray, rightArray);
    }

}
