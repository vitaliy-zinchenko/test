package zinjvi.cash;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class Customer implements Runnable, CustomerMBean {

    private Belt belt;

    public Customer(Belt belt) {
        this.belt = belt;
    }

    @Override
    public void hiCashier() {
        System.out.println("Hi Cashier!");
    }

    @Override
    public void addProduct(String name, int position) {
        belt.addProduct(new Product(name, position));
    }

    @Override
    public void run() {
        while(true) {

        }
    }

}
