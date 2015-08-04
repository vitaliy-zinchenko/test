package zinjvi.algo.mapReduce.core;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface Reducer<K, V> {
    void reduce(K key, List<V> value, Map<K, V> result);
}
