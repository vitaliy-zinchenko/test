package zinjvi.algo.mapReduce.core;

public interface MapperFactory<K, V> {

    Mapper<K, V> create();

}
