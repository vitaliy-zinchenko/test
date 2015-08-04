package zinjvi.algo.mapReduce.core;

public interface ReducerFactory<K, V> {
    Reducer<K, V> create();
}
