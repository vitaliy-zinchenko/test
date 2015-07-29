package zinjvi.cash;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

//@ThreadSafe
public class Belt implements Runnable {

    public static final int MOVE_STEP_TIME = 1000;
    public static final int NEAR_POSITION_SIZE = 3;
    public static final int QUEUE_INITIAL_CAPACITY = 10;

    private volatile boolean canMove;

    private Queue<Product> queue;

    private Semaphore semaphore;

    public Belt() {
        queue = new PriorityBlockingQueue<Product>(QUEUE_INITIAL_CAPACITY, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getPosition() - o2.getPosition();
            }
        });
        semaphore = new Semaphore(0);
    }

    public void addProduct(Product product) {
        queue.add(product);
        releaseSemaphoreIfNeed(product.getPosition());
        System.out.println("Product added: " + product);
    }

    public Product getFirst() {
        return queue.peek();
    }

    public Product pop() throws InterruptedException {
        semaphore.acquire();
        return queue.poll();
    }

    public void stop() {
        canMove = false;
    }

    public void start() {
        canMove = true;
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void run() {
        System.out.println("Belt | run()");
        while(true) {
            if(canMove) {
                moveAllProducts();
                try {
                    Thread.sleep(MOVE_STEP_TIME);
                } catch (InterruptedException e) {
                    return;
                }
            } else {
                synchronized (this) {
                    try {
                        while(!canMove) {
                            System.out.println("Belt | waiting");
                            this.wait();
                            System.out.println("Belt | finished waiting");
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

    private void moveAllProducts() {
        System.out.println("Moving all products");
        for (Product product: queue) {
            releaseSemaphoreIfNeed(product.move());
        }
        System.out.println("Belt | positions after moving: " + getPositionsAsString());
    }

    private void releaseSemaphoreIfNeed(int productPosition) {
        if(productPosition <= NEAR_POSITION_SIZE) {
            semaphore.release();
        }
    }

    private String getPositionsAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Product product: queue) {
            stringBuilder.append(product.getPosition() + ", ");
        }
        return stringBuilder.toString();
    }


}
