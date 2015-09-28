package zinjvi.algo.sort;

import com.google.common.base.Stopwatch;

import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vitaliy on 8/17/2015.
 */
public class BubbleForkConcurrencySorter<T> implements Sorter<T> {

    public static final AtomicLong ATOMIC_LONG = new AtomicLong(0);

    private Executor executor;

    public BubbleForkConcurrencySorter(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void sort(T[] arr, Comparator<T> comparator) {
        CountDownLatch complete = new CountDownLatch(arr.length);
//        System.out.println("Sorting arr=" + Arrays.toString(arr));
        AtomicInteger currentSortSize = new AtomicInteger(arr.length);

        executor.execute(new Sorter<T>(arr, currentSortSize, comparator, complete, null, executor));

        try {
            complete.await();
        } catch (InterruptedException e) {
            //
        }
    }

    class Sorter<I> implements Runnable {

        private I[] arr;
        private AtomicInteger currentSortSize;
        private Comparator<I> comparator;
        private CountDownLatch complete;
        private ReentrantLock[] locks;
        private Semaphore parentFreeItems;
        private Semaphore freeItems;
        private Executor executor;

        public Sorter(I[] arr, AtomicInteger currentSortSize, Comparator<I> comparator, CountDownLatch complete, Semaphore parentFreeItems, Executor executor) {
            this.arr = arr;
            this.currentSortSize = currentSortSize;
            this.comparator = comparator;
            this.complete = complete;
            this.parentFreeItems = parentFreeItems;
            this.freeItems = new Semaphore(0);
            this.executor = executor;
        }

        @Override
        public void run() {
//            System.out.println("run " + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());

            int sortSize = currentSortSize.getAndDecrement();
//            System.out.println("after lock 0 sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
            for (int i = 1; i < sortSize; i++) {
//                System.out.println("i=" + i + " sortSize=" + sortSize + " arr[i]=" + arr[i] + " arr[i - 1]=" + arr[i - 1] + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
                if(parentFreeItems != null) {
                    parentFreeItems.acquireUninterruptibly(i);
                }

                if (comparator.compare(arr[i - 1], arr[i]) < 0) {
//                    System.out.println("move sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
                    I rightValue = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = rightValue;
//                    System.out.println("after move sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
                }

                if (freeItems != null) {
                    freeItems.release(i);
                }

                if(i==1) {
                    executor.execute(new Sorter<>(arr, currentSortSize, comparator, complete, freeItems, executor));
                }
            }
            complete.countDown();
        }
    }
}
