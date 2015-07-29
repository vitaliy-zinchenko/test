package zinjvi.cash;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class Product {

    private static final int DEFAULT_ITEMS = 1;

    private String name;

    private AtomicInteger position;

    public Product(String name, int position) {
        this.name = name;
        this.position = new AtomicInteger(position);
    }

    public int move() {
        return move(DEFAULT_ITEMS);
    }

    public int move(int items) {
        return position.decrementAndGet();
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position.get();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (position != null ? !position.equals(product.position) : product.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}
