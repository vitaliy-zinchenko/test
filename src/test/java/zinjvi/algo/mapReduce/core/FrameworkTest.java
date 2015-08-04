package zinjvi.algo.mapReduce.core;

import com.google.common.collect.ImmutableMap;
import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import zinjvi.Timer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class FrameworkTest {

    private Framework<String, Long> framework;

    @Before
    public void before() {
        framework = createFramework();
    }

    protected abstract Framework<String, Long> createFramework();

    private class WordCountMapper implements Mapper<String, Long> {

        public static final long ONE = 1L;

        @Override
        public Map<String, Long> map(String line) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            Map<String, Long> registeredWords = new HashMap<>();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                Long registeredCount = registeredWords.get(token);
                if(registeredCount != null) {
                    registeredWords.put(token, ++registeredCount);
                } else {
                    registeredWords.put(token, ONE);
                }
            }
            return registeredWords;
        }
    }

    private class WordCountReducer implements Reducer<String, Long> {
        @Override
        public void reduce(String key, List<Long> value, Map<String, Long> result) {
            try {
                Long sum = 0L;
                for (Long count : value) {
                     sum += count;
                }
                result.put(key, sum);
            } catch (Exception e) {
//                System.err.println("VALUE: " + value);
                e.printStackTrace();
            }
        }
    }

    private class WordCountMapperFactory implements MapperFactory<String, Long> {
        @Override
        public Mapper<String, Long> create() {
            return new WordCountMapper();
        }
    }

    private class WordCountReducerFactory implements ReducerFactory<String, Long> {
        @Override
        public Reducer<String, Long> create() {
            return new WordCountReducer();
        }
    }

    private void prettyPrint(Map<String, Long> map) {
        for (Map.Entry<String, Long> entry: map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    @Test
    public void testProcess() throws IOException {
        // given
        InputStream in = new StringInputStream(
                "q w e\n" +
                "e\n" +
                "e r\n" +
                "r\n" +
                "r\n" +
                "w r");
                //FrameworkTest.class.getClassLoader().getResourceAsStream("zinjvi/algo/mapReduce/data.txt");

        //when
        Map<String, Long> actualResult = framework.process(in, new WordCountMapperFactory(), new WordCountReducerFactory());

        //then
        Map<String, Long> expectedResult = new ImmutableMap.Builder<String, Long>()
                .put("q", 1L)
                .put("w", 2L)
                .put("e", 3L)
                .put("r", 4L)
                .build();
        Assert.assertEquals(expectedResult, actualResult);
        prettyPrint(actualResult);
    }

    @Test
    public void testProcessFromFile() throws Exception {
        // given
        final String dataFilePath = "zinjvi/algo/mapReduce/data.txt";

        //when
        long avg = Timer.run(10, () -> {
            InputStream in = FrameworkTest.class.getClassLoader().getResourceAsStream(dataFilePath);
            framework.process(in, new WordCountMapperFactory(), new WordCountReducerFactory());
        });
        System.out.println("AVG Time = " + avg + " millis");


//        Stopwatch stopwatch = Stopwatch.createStarted();
//        Map<String, Long> actualResult = framework.process(in, new WordCountMapperFactory(), new WordCountReducerFactory());
//        System.out.println("Elapsed: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        //then
//        prettyPrint(actualResult);
    }


}