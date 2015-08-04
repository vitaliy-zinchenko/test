package zinjvi.algo.mapReduce.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleThreadFramework<K, V> extends AbstractFramework<K, V> implements Framework<K, V> {

    @Override
    public Map<K, V> process(InputStream in, MapperFactory<K, V> mapperFactory, ReducerFactory<K, V> reducerFactory) throws IOException {
        System.out.println("Creating reader");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        System.out.println("Reading lines and executing mappers");
        Map<K, List<V>> reduceInput = new HashMap<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Map<K, V> mapResult = mapperFactory.create().map(line);
            addMapResult(mapResult, reduceInput);
        }

        System.out.println("Reducing");
        Map<K, V> result = new HashMap<>();
        for (Map.Entry<K, List<V>> entry: reduceInput.entrySet()) {
            reducerFactory.create().reduce(entry.getKey(), entry.getValue(), result);
        }

        return result;
    }
}
