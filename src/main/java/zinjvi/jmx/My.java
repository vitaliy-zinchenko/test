package zinjvi.jmx;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class My implements MyMBean {

    @Override
    public void f() {
        System.out.println("print");
    }
}
