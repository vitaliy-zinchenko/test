package zinjvi.cash;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class MarketMain {

    private Executor executor;

    private JmxRegister jmxRegister;

    private Belt belt;

    private Detector detector;

    private Customer customer;

    private Cashier cashier;

    public static void main(String[] args) {
        MarketMain marketMain = new MarketMain();
        marketMain.init();
    }

    public void init() {
        executor = Executors.newFixedThreadPool(5);
        jmxRegister = new JmxRegister();

        belt = new Belt();
        executor.execute(belt);

        detector = new Detector(belt);
        executor.execute(detector);

        customer = new Customer(belt);
        executor.execute(customer);
        jmxRegister.register("zinjvi.cash:type=Customer", customer);

        cashier = new Cashier(belt);
        executor.execute(cashier);

    }

}
