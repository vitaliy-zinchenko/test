package zinjvi.algo.mapReduce.core;

public class ConcurrentFrameworkTest extends FrameworkTest {
    @Override
    protected Framework<String, Long> createFramework() {
        return new ConcurrencyFramework<>();
    }
}
