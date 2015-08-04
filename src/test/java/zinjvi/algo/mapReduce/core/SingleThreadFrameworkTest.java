package zinjvi.algo.mapReduce.core;

public class SingleThreadFrameworkTest extends FrameworkTest {
    @Override
    protected Framework<String, Long> createFramework() {
        return new SingleThreadFramework<>();
    }
}
