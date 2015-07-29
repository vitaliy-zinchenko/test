package zinjvi.mt;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<T> implements Buffer<T> {

    private T[] items;

    private int count;

    private int takeIndex;

    private  int putIndex;

    private Lock lock;
    private Condition notEmpty;
    private Condition notFull;

    public BoundedBuffer(int bound) {
        items = (T[]) new Object[bound];
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    @Override
    public synchronized void put(T item) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while(isFull()) {
                notFull.await();
            }

            items[putIndex] = item;
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            ++count;

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized T take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (isEmpty()) {
                notEmpty.await();
            }

            T item = items[takeIndex];
            items[takeIndex] = null;
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            ++count;

            notFull.signal();

            return item;
        } finally {
            lock.unlock();
        }
    }

    private boolean isEmpty() {
        return count == 0;
    }

    private boolean isFull() {
        return count == items.length;
    }
}
