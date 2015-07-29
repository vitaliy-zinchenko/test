package zinjvi.cash;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class Detector implements Runnable {


    public static final int DETECT_TIME = 1000;
    private Belt belt;

    public Detector(Belt belt) {
        this.belt = belt;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Product product = belt.getFirst();
                if(product == null) {
                    System.out.println("Detector | No products");
                    continue;
                }
                if(product.getPosition() <= 0) {
                    System.out.println("Detector | product is near. Try to stop belt.");
                    belt.stop();
                } else {
                    System.out.println("Detector | product is far. Try to start belt.");
                    belt.start();
                }
            } finally {
                try {
                    System.out.println("Detector | is waiting");
                    Thread.sleep(DETECT_TIME);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
