package zinjvi.algo.sort;

import com.google.common.base.Stopwatch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vitaliy on 8/17/2015.
 */
public class BubbleConcurrencySorter<T> implements Sorter<T> {

    public static final AtomicLong ATOMIC_LONG = new AtomicLong(0);

    private Executor executor;

    public BubbleConcurrencySorter(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void sort(T[] arr, Comparator<T> comparator) {
        CountDownLatch complete = new CountDownLatch(arr.length);
        ReentrantLock[] locks = createLocks(arr.length);
//        System.out.println("Sorting arr=" + Arrays.toString(arr));
        AtomicInteger currentSortSize = new AtomicInteger(arr.length);
        for (int i = 0; i < arr.length; i++) {
//        for (int sortSize = arr.length; sortSize > 0; sortSize--) {
//            System.out.println("sortSize=" + sortSize);
            executor.execute(new Sorter<T>(arr, currentSortSize, comparator, complete, locks));
        }
        try {
            complete.await();
        } catch (InterruptedException e) {
            //
        }
    }

    private ReentrantLock[] createLocks(int size) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        ReentrantLock[] locks = new ReentrantLock[size];
        for (int i = 0; i < size; i++) {
            locks[i] = new ReentrantLock();
        }

        stopwatch.stop();
        System.out.println("Time to create locks = " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return locks;
    }

    class Sorter<I> implements Runnable {

        private I[] arr;
        private AtomicInteger currentSortSize;
        private Comparator<I> comparator;
        private CountDownLatch complete;
        private ReentrantLock[] locks;

        public Sorter(I[] arr, AtomicInteger currentSortSize, Comparator<I> comparator, CountDownLatch complete, ReentrantLock[] locks) {
            this.arr = arr;
            this.currentSortSize = currentSortSize;
            this.comparator = comparator;
            this.complete = complete;
            this.locks = locks;
        }

        @Override
        public void run() {
//            System.out.println("run " + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
//            System.out.println("before lock 0 " + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());

//            Stopwatch stopwatch = Stopwatch.createStarted();
            locks[0].lock();
//            stopwatch.stop();
//            ATOMIC_LONG.addAndGet(stopwatch.elapsed(TimeUnit.MICROSECONDS));
//            System.out.println("Time to lock MICROSECONDS = " + stopwatch.elapsed(TimeUnit.MICROSECONDS));
//            System.out.println("Time to lock MICROSECONDS = " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

            int sortSize = currentSortSize.getAndDecrement();
//            System.out.println("after lock 0 sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
            for (int i = 1; i < sortSize; i++) {
//                System.out.println("i=" + i + " sortSize=" + sortSize + " arr[i]=" + arr[i] + " arr[i - 1]=" + arr[i - 1] + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());

//                System.out.println("before lock i sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
                locks[i].lock();
//                System.out.println("after lock i sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());

                if (comparator.compare(arr[i - 1], arr[i]) < 0) {
//                    System.out.println("move sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
                    I rightValue = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = rightValue;
//                    System.out.println("after move sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
                }

//                System.out.println("before unlock i-1 sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
//                Stopwatch stopwatch2 = Stopwatch.createStarted();
                locks[i - 1].unlock();
//                stopwatch2.stop();
//                ATOMIC_LONG.addAndGet(stopwatch.elapsed(TimeUnit.MICROSECONDS));
//                System.out.println("after unlock i-1 sortSize=" + sortSize + " arr=" + Arrays.toString(arr) + " " + Thread.currentThread().getName());
            }
            complete.countDown();
        }
    }
}
