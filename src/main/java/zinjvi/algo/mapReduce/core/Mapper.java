package zinjvi.algo.mapReduce.core;

import java.util.Map;

@FunctionalInterface
public interface Mapper<K, V> {
    //TODO | it is bad idea to hardcode the String type
    Map<K, V> map(String line);
}
