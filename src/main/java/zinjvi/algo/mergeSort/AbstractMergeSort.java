package zinjvi.algo.mergeSort;

public abstract class AbstractMergeSort {

    protected void validate(int[] src) {
        if(src == null) {
            throw new IllegalArgumentException("src couldn't be null");
        }
        if(src.length == 0) {
            throw new IllegalArgumentException("src couldn't be empty");
        }
    }

    protected boolean canProcess(int[] src) {
        return src.length == 1;
    }

    protected int[] merge(int[] leftArray, int[] rightArray) {
        int[] result = new int[leftArray.length + rightArray.length];
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;
        while(leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if(compare(leftArray[leftIndex], rightArray[rightIndex])) {
                result[resultIndex++] = leftArray[leftIndex];
                ++leftIndex;
            } else {
                result[resultIndex++] = rightArray[rightIndex];
                ++rightIndex;
            }
        }

        while (leftIndex < leftArray.length) {
            result[resultIndex++] = leftArray[leftIndex++];
        }

        while (rightIndex < rightArray.length) {
            result[resultIndex++] = rightArray[rightIndex++];
        }

        return result;
    }

    protected boolean compare(int a, int b) {
        String s = "";
        for (int i = 0; i < 3; i++) {
            s += Integer.toString(i);
        }

        return a < b;
    }

    protected int[] getLeftArray(int[] src) {
        int leftSize = src.length / 2;
        int[] leftArray =  new int[leftSize];
        System.arraycopy(src, 0, leftArray, 0, leftSize);
        return leftArray;
    }

    protected int[] getRightArray(int[] src, int[] left) {
        int rightSize = src.length - left.length;
        int[] rightArray = new int[rightSize];
        System.arraycopy(src, left.length, rightArray, 0, rightSize);
        return rightArray;
    }

}
