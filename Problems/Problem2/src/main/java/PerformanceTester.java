import java.util.ArrayList;
import java.util.stream.DoubleStream;

public class PerformanceTester {
    private static final int WARM_UP_LOOPS_COUNT = 20_000_000;
    private static final int WARM_UP_MESSAGE_COUNT = 3;

    private static final int TEST_LOOPS_COUNT = 10;
    private static final int REPETITIONS_PER_TEST_LOOP = 3;

    private static void warmUp()
    {
        int valueHolder;
        for (int i = 1; i <= WARM_UP_LOOPS_COUNT; ++i)
        {
            if (i % (WARM_UP_LOOPS_COUNT / WARM_UP_MESSAGE_COUNT) == 0) {
                System.out.println("Warming up ...");
            }
            valueHolder = i * i;
        }

    }


    public static void printTime(Runnable runnable, int testLoopsCount, int repetitionsPerTestLoop) {
        warmUp();

        var executionTimes = new ArrayList<Double>();
        long start;
        for (int i = 0; i < testLoopsCount; ++i)
        {
            start = System.currentTimeMillis();
            for (int j = 0; j < repetitionsPerTestLoop; ++j)
            {
                runnable.run();
            }
            executionTimes.add((double) (System.currentTimeMillis() - start) / REPETITIONS_PER_TEST_LOOP);
        }
        double avg = executionTimes.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        double std = Math.sqrt(executionTimes.stream().mapToDouble(Double::doubleValue).map(
                time -> (time - avg) * (time - avg)).sum() / Math.max(TEST_LOOPS_COUNT - 1, 1));
        System.out.printf("Average time: %f ms. Standard deviation: %f ms\n", avg, std);
    }

    public static void printTime(Runnable runnable) {
        printTime(runnable, TEST_LOOPS_COUNT, REPETITIONS_PER_TEST_LOOP);
    }
}
