package zinjvi;

import java.util.concurrent.TimeUnit;

/**
 * Created by Vitaliy_Zinchenko on 07.07.2015.
 */
public class Utils {
    public static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
