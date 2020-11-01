import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class App {
    static private final CyclicBarrier cyclicBarrier = new CyclicBarrier(3,
            () -> System.out.println("Barrier reached"));

    private static void createAndStartThreadWithWaitTime(long timeOutMillis) {
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread start");
                    synchronized (this) {
                        this.wait(timeOutMillis);
                    }
                    System.out.println("Thread waiting time over");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void main(String... args) {
        System.out.println(cyclicBarrier.getParties());
        createAndStartThreadWithWaitTime(5000);
        createAndStartThreadWithWaitTime(10000);
        createAndStartThreadWithWaitTime(3000);
    }
}

