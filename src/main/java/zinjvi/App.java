package zinjvi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );

        Shared shared = new Shared();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        executor.execute(new Action(shared));
        executor.execute(new Action(shared));

        executor.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println(shared.getCount());
    }

    private static class Shared {
        private transient Lock lock = new ReentrantLock();
        private int count = 0;

        public Lock getLock() {
            return lock;
        }

        public void setLock(Lock lock) {
            this.lock = lock;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private static class Action implements Runnable {

        // @GuardedBy(shared.lock)
        private Shared shared;

        public Action(Shared shared) {
            this.shared = shared;
        }

        @Override
        public void run() {
//            shared.getLock().lock();
//            try {
            synchronized (shared.getLock()) {
                System.out.println("start " + System.currentTimeMillis());
                int count = shared.getCount();
                Utils.sleep(1);
                shared.setCount(++count);
            }
//            } finally {
//                shared.getLock().unlock();
//            }
        }
    }

}
