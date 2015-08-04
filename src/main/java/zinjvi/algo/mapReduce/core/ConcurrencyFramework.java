package zinjvi.algo.mapReduce.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyFramework<K, V> extends AbstractFramework<K, V> implements Framework<K, V> {

    private ExecutorService executorService;
//    private CompletionService<Map<K, V>> mapExecutorService;
//    private CompletionService<Map<K, V>> reduceExecutorService;

    AtomicInteger submitted = new AtomicInteger(0);
    AtomicInteger finished = new AtomicInteger(0);

    public ConcurrencyFramework() {
        this.executorService = Executors.newFixedThreadPool(4);
//        this.mapExecutorService = new ExecutorCompletionService<Map<K, V>>(executorService);
//        this.reduceExecutorService = new ExecutorCompletionService<Map<K, V>>(executorService);
    }

    private class MapTask implements Runnable {

        private String line;
        private Mapper<K, V> mapper;
        private ConcurrentMap<K, List<V>> reduceInput;
        private Semaphore readComplete;

        public MapTask(String line, Mapper<K, V> mapper, ConcurrentMap<K, List<V>> reduceInput,Semaphore readComplete) {
            this.line = line;
            this.mapper = mapper;
            this.reduceInput = reduceInput;
            this.readComplete = readComplete;
        }

        @Override
        public void run() {
            addMapResult(mapper.map(line), reduceInput);
            readComplete.release();
        }
    }

    private class ReduceTask implements Runnable {

        private Map.Entry<K, List<V>> entry;
        private ConcurrentMap<K, V> result;
        private Reducer<K, V> reducer;
        private CountDownLatch reduceComplete;

        public ReduceTask(Map.Entry<K, List<V>> entry, ConcurrentMap<K, V> result, Reducer<K, V> reducer, CountDownLatch reduceComplete) {
            this.entry = entry;
            this.result = result;
            this.reducer = reducer;
            this.reduceComplete = reduceComplete;
        }

        @Override
        public void run() {
            reducer.reduce(entry.getKey(), entry.getValue(), result);
            reduceComplete.countDown();
            finished.incrementAndGet();
        }
    }

    @Override
    public Map<K, V> process(InputStream in, MapperFactory<K, V> mapperFactory, ReducerFactory<K, V> reducerFactory) throws IOException {
        System.out.println("Creating reader");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        System.out.println("Reading lines and executing mappers");
        int lineNumber = 0;
        Semaphore readComplete = new Semaphore(0);
        ConcurrentMap<K, List<V>> reduceInput = new ConcurrentHashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            executorService.submit(new MapTask(line, mapperFactory.create(), reduceInput, readComplete));
            lineNumber++;
        }
        try {
            System.out.println("Wait until all MapTask complete work");
            readComplete.acquire(lineNumber);
        } catch (InterruptedException e) {
            e.printStackTrace(); //TODO
        }
//        ConcurrentMap<K, List<V>> reduceInput = new ConcurrentHashMap<>();
//        try {
//            for (int i = 0; i < lineNumber; i++) {
//                Future<Map<K, V>> future = mapExecutorService.take();
//                try {
//                    addMapResult(future.get(), reduceInput);
//                } catch (ExecutionException e) {
//                    e.printStackTrace(); //TODO
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace(); //TODO
//        }
//
        System.out.println("Executing reducing, reduceInput.size() = " + reduceInput.size());
        CountDownLatch reduceComplete = new CountDownLatch(reduceInput.size());
        ConcurrentMap<K, V> result = new ConcurrentHashMap<>();

        System.out.println("reduceComplete.getCount() = " + reduceComplete.getCount());
        for (Map.Entry<K, List<V>> entry: reduceInput.entrySet()) {
            executorService.execute(new ReduceTask(entry, result, reducerFactory.create(), reduceComplete));
//            executorService.submit(() -> {
//                reducerFactory.create()
//                .reduce(entry.getKey(), entry.getValue(), result);
//                reduceComplete.countDown();
//                finished.incrementAndGet();
//            });
            submitted.incrementAndGet();
        }


        //TEST
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        return;
//                    }
//                    System.out.println("n: " + reduceComplete.getCount() + " s: " + submitted.get() + " f: " + finished.get());
//
//                }
//            }
//        });
//        t.setDaemon(true);
//        t.start();
        //TEST


        try {
            reduceComplete.await();
        } catch (InterruptedException e) {
            e.printStackTrace();//todo
        }


        return result;
    }
}
