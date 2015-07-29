package zinjvi.cash;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BeltTestM {

    private class BeltTest1 extends MultithreadedTestCase {

        ArrayBlockingQueue<Integer> buf;

        @Override public void initialize() {
            buf = new ArrayBlockingQueue<Integer>(1);
        }

        public void thread1() throws InterruptedException {
            buf.put(42);
            buf.put(17);
            assertTick(1);
        }

        public void thread2() throws InterruptedException {
            waitForTick(1);
            assertEquals(Integer.valueOf(42), buf.take());
            assertEquals(Integer.valueOf(17), buf.take());
        }

        @Override public void finish() {
            assertTrue(buf.isEmpty());
        }
    }

    @Test
    public void testBelt() throws Throwable {
        TestFramework.runOnce(new BeltTest1());
    }

    private class AtomicTest extends MultithreadedTestCase {

        private AtomicInteger atomicInteger;

        @Override
        public void initialize() {
            atomicInteger = new AtomicInteger(1);
        }

        public void thread1() {
            synchronized (atomicInteger) {
                while(!atomicInteger.compareAndSet(2, 3)) {
                    System.out.println("loop");
                    try {
                        atomicInteger.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }

        public void thread2() throws InterruptedException {
            assertTrue(atomicInteger.compareAndSet(1, 2));
            synchronized (atomicInteger) {
                atomicInteger.notify();
            }
        }

        @Override
        public void finish() {
            assertEquals(3, atomicInteger.get());
        }
    }

    @Test
    public void testAtomic() throws Throwable {
        TestFramework.runOnce(new AtomicTest());
    }


}