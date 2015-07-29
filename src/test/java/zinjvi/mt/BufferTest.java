package zinjvi.mt;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BufferTest {

    public static final int DEFAULT_BOUND = 10;

    private Buffer<String> buffer;

    @Before
    public void setUp() throws Exception {
        buffer = new BoundedBuffer<>(DEFAULT_BOUND);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBlockWhenFull() throws Throwable {
        TestFramework.runOnce(new MultithreadedTestCase () {
            @Override
            public void initialize() {
                buffer = new BoundedBuffer<>(3);
            }

            public void thread1() throws InterruptedException {
                buffer.put("1");
                buffer.put("2");
                buffer.put("3");

                buffer.put("4");
                assertTick(1);
            }

            public void thread2() throws InterruptedException {
                waitForTick(1);
                assertEquals("1", buffer.take());
            }
        });
    }

    @Test
    public void testBlockWhenEmpty() throws Throwable {
        TestFramework.runOnce(new MultithreadedTestCase () {
            @Override
            public void initialize() {
                buffer = new BoundedBuffer<>(3);
            }

            public void thread1() throws InterruptedException {
                assertEquals("1", buffer.take());
                assertTick(1);
            }

            public void thread2() throws InterruptedException {
                waitForTick(1);
                buffer.put("1");
            }
        });
    }
}