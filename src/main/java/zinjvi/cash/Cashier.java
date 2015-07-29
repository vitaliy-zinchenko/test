package zinjvi.cash;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class Cashier implements Runnable {

    private Belt belt;

    public Cashier(Belt belt) {
        this.belt = belt;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Product product = belt.pop();
                System.out.println("Cashier | poped: " + product);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
