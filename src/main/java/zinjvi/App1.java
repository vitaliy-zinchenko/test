package zinjvi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vitaliy_Zinchenko on 10.07.2015.
 */
public class App1 {
    public static void main(String[] args) {
//        final Semaphore semaphore = new Semaphore(1);
        final Lock lock = new ReentrantLock();


        new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to acquire semaphore");
                    try {
//                        semaphore.acquire();
                        lock.lockInterruptibly();
                    } catch (InterruptedException e) {
                        return;
                    }
                    System.out.println("acquired semaphore");

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to release semaphore ------------------------------");
//                    semaphore.release();
//                    semaphore.release();
//                    semaphore.release();
                    lock.unlock();
                    System.out.println("released semaphore ------------------------------");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }.start();

    }
}
