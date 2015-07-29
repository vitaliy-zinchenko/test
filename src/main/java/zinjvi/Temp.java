package zinjvi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Temp {

    private static BufferedWriter bufferedWriter;

    public static void main(String[] args) throws IOException, InterruptedException {
        StringWriter stringWriter = new StringWriter();
        bufferedWriter = new BufferedWriter(stringWriter);

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("start1");
                String[] strings = {"q", "w", "e", "r", "t"};
                synchronized (bufferedWriter) {
                    for (String s : strings) {
                        try {
                            bufferedWriter.write(s);
                            bufferedWriter.flush();
                            Thread.yield();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                countDownLatch.countDown();
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("start2");
                String[] strings = {"1", "2", "3", "4", "5"};
                synchronized (bufferedWriter) {
                    for (String s : strings) {
                        try {
                            bufferedWriter.write(s);
                            bufferedWriter.flush();
                            Thread.yield();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                countDownLatch.countDown();
            }
        });


        countDownLatch.await();

        System.out.println(stringWriter.getBuffer().toString());

    }



}