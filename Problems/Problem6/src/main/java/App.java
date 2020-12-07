import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private static CustomConcurrentQueue<Integer> concurrentQueue = new CustomConcurrentQueue<Integer>();
    private static AtomicInteger counter = new AtomicInteger(0);
    private static final int MAX_COUNTER_VALUE = 500;
    private static List<Integer> values = new ArrayList<Integer>(MAX_COUNTER_VALUE);

    private static void incrementCounterAndPutValueToQueue() {
        while (counter.get() < MAX_COUNTER_VALUE) {
            System.out.printf("[PUSH] Thread #%d, value %d\n", Thread.currentThread().getId(), counter.get());
            concurrentQueue.push(counter.getAndIncrement());
        }
    }

    private static void getValueFromQueue() {
        Integer value = concurrentQueue.pop();

        while (values.size() != MAX_COUNTER_VALUE) {
            if (value != null) {
                System.out.printf("[POP] Thread #%d, value %d\n", Thread.currentThread().getId(), value);
                values.add(value);
            }
            value = concurrentQueue.pop();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(App::incrementCounterAndPutValueToQueue).start();
        new Thread(App::incrementCounterAndPutValueToQueue).start();
        new Thread(App::incrementCounterAndPutValueToQueue).start();

        var worker = new Thread(App::getValueFromQueue);
        worker.start();
        worker.join();
        
        System.out.println(values);

    }
}
