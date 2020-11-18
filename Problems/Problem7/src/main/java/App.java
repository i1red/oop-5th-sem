import java.util.concurrent.BrokenBarrierException;

public class App {
    static private final CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3,
            () -> System.out.println("Barrier reached"));

    private static void createAndStartThreadWithWaitTime(long timeOutMillis) {
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread start");
                    synchronized (this) {
                        this.wait(timeOutMillis / 2);
                    }
                    System.out.println("Half of waiting time is over");
                    cyclicBarrier.await();

                    synchronized (this) {
                        this.wait(timeOutMillis / 2);
                    }
                    System.out.println("Waiting time is over");
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

