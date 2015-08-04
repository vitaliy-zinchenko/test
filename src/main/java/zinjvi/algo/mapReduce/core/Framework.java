package zinjvi.algo.mapReduce.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface Framework<K, V> {
    public Map<K, V> process(InputStream in, MapperFactory<K, V> mapperFactory, ReducerFactory<K, V> reducerFactory) throws IOException;
}
