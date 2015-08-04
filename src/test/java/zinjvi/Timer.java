package zinjvi;

public class Timer {

    public static long run(int loops, TestedCode method) throws Exception {
        long sum = 0;
        for (int i = 0; i < loops; i++) {
            long before = System.currentTimeMillis();
            method.test();
            long after = System.currentTimeMillis();
            long dist = after - before;
            sum += dist;
            System.out.println("Dist = " + dist + "; Sum = " + sum);
        }
        return sum / loops;
    }

}
