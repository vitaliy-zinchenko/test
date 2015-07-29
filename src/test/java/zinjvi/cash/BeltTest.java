package zinjvi.cash;

import net.jodah.concurrentunit.Waiter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class BeltTest {

    private Belt belt;
    private Belt beltSpy;

    @Before
    public void setUp() throws Exception {
        belt = new Belt();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFirstProduct() {
        // given
        // when
        Product product = new Product("test", 10);
        belt.addProduct(product);

        Product product2 = new Product("test2", 5);
        belt.addProduct(product2);

        // then
        Product firstProduct = belt.getFirst();
        Assert.assertEquals(product2, firstProduct);
    }

    @Test
    public void testGetFirstProductWhenQueueIsEmpty() {
        // given
        // when
        // then
        Product firstProduct = belt.getFirst();
        Assert.assertNull(firstProduct);
    }

    @Test
    public void testGetFirstProductWhenCanNotMove() throws InterruptedException {
        // given
        Product product = new Product("test", 10);
        belt.addProduct(product);
        belt.stop();
        Thread thread = new Thread(belt);

        // when
        thread.start();
        thread.join();

        // then
        Assert.assertEquals(10, product.getPosition());
    }

    @Test
    public void testGetFirstProductWhenCanMove() throws InterruptedException {
        Product product = new Product("test", 10);
        belt.addProduct(product);
        belt.start();
        Thread thread = new Thread(belt);

        // when
        thread.start();
        Thread.sleep(Belt.MOVE_STEP_TIME / 2);
        belt.stop();

        // then
        Assert.assertEquals(9, product.getPosition());
    }

    @Test
    public void shouldWakeUpAfterStart() throws InterruptedException {
        Product product = new Product("test", 10);
        belt.addProduct(product);
        belt.stop();
        Thread thread = new Thread(belt);

        // when
        thread.start();
        Thread.sleep(100);
        belt.start();
        Thread.sleep(Belt.MOVE_STEP_TIME / 2);

        // then
        Assert.assertEquals(9, product.getPosition());
    }

    @Test
    public void shouldReturnProduct() throws InterruptedException {
        // given
        Product product = new Product("test", 0);
        belt.addProduct(product);
        Thread thread = new Thread(belt);
        thread.start();

        // when
        Product result = belt.pop();

        // then
        Assert.assertTrue(product == result);
    }

    @Ignore
//    @Tes
    // TODO | how to test this?
    public void shouldBlockThreadWhenBeltEmpty() throws Throwable {
        // given
//        Product product = new Product("test", 2);
//        belt.addProduct(product);
        belt.start();
        Thread thread = new Thread(belt);
        thread.start();

        final Waiter waiter = new Waiter();

        // when
        final CountDownLatch threadStarted = new CountDownLatch(1);
        Thread testThread = new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
                    threadStarted.countDown();
//                    belt.pop();
                    Assert.fail("fff");
                waiter.fail("In this test case pop() method should blocks thread");
//                } catch (InterruptedException e) {
//                    return;
//                }
            }
        });
        testThread.setDaemon(true);
        testThread.start();
//        testThread.
//        threadStarted.await(1, TimeUnit.SECONDS);
//        Assert.assertTrue(testThread.isAlive());
//        testThread.interrupt();
        // then
//        Assert.assertFalse("In this test case pop() method should blocks thread", fail.get());
        waiter.await(1000);
    }


}