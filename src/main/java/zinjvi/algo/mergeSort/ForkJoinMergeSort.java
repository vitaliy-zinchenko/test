package zinjvi.algo.mergeSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMergeSort extends AbstractMergeSort implements MergeSort {

    private ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Override
    public int[] sort(int[] array) {
        return forkJoinPool.invoke(new SortTask(array));
    }

    private class SortTask extends RecursiveTask<int[]> {

        int[] src;

        public SortTask(int[] src) {
            this.src = src;
        }

        @Override
        protected int[] compute() {
            ForkJoinMergeSort.super.validate(src);
            if(src.length == 1) {
                return src;
            } else if (src.length < 10_000_000 / (4 * 50)) {
                return sortDirectly(src);
            } else {
                int[] leftArray = getLeftArray(src);
                int[] rightArray = getRightArray(src, leftArray);

                SortTask leftSortTask = new SortTask(leftArray);
                SortTask rightSortTask = new SortTask(rightArray);

                leftSortTask.fork();
                rightSortTask.fork();

                return merge(leftSortTask.join(), rightSortTask.join());
            }
        }

        private int[] sortDirectly(int[] src) {
            if(src.length == 1) {
                return src;
            }
            int[] leftArray = getLeftArray(src);

            leftArray = sortDirectly(leftArray);
            int[] rightArray = sortDirectly(getRightArray(src, leftArray));

            return merge(leftArray, rightArray);
        }

    }


}
