public class App {
    private static volatile int counter = 0;
    private static final int ITERATIONS_COUNT = 100000;
    private static CustomReentrantLock reentrantLock = new CustomReentrantLock();
    private static boolean enableLocking = true;

    private static Runnable changeCounterFactory(int value) {
        return enableLocking ? () -> {
            for (int i = 0; i < ITERATIONS_COUNT; ++i) {
                reentrantLock.lock();
                counter += value;
                reentrantLock.unlock();
            }
        } : () -> {
            for (int i = 0; i < ITERATIONS_COUNT; ++i) {
                counter += value;
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        var t1 = new Thread(changeCounterFactory(1));
        var t2 = new Thread(changeCounterFactory(1));
        var t3 = new Thread(changeCounterFactory(-2));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(counter);
    }
}
