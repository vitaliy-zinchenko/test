package zinjvi.algo.mapReduce.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractFramework<K, V> {
    protected void addMapResult(Map<K, V> mapResult, Map<K, List<V>> reduceInput) {
        for (Map.Entry<K, V> entry: mapResult.entrySet()) {
            List<V> valuesOfKey = reduceInput.get(entry.getKey());
            if(valuesOfKey == null) {
                valuesOfKey = Collections.synchronizedList(new ArrayList<>());
                reduceInput.put(entry.getKey(), valuesOfKey);
//                for (V v: valuesOfKey) {
//                    if(v == null) {
//                        throw new RuntimeException("null");
//                    }
//                }
            }
            V value = entry.getValue();
            valuesOfKey.add(value);
        }
    }
}
