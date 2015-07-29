package zinjvi.mt;

public interface Buffer<T> {

    void put(T item) throws InterruptedException;

    T take() throws InterruptedException;

}
