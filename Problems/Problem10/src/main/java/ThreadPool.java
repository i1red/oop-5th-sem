import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPool implements Executor, AutoCloseable {
    private final Queue<Runnable> tasksQueue;
    private final AtomicInteger tasksCount;
    private final List<Thread> threads;
    private volatile boolean isClosed;

    private void startThreads(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            var thread = new Thread(() -> {
                while (this.isClosed || this.tasksCount.get() > 0) {
                    Runnable nextTask = this.tasksQueue.poll();

                    if (nextTask != null) {
                        nextTask.run();
                        this.tasksCount.decrementAndGet();
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }
    }

    public ThreadPool(int threadCount) {
        this.tasksQueue = new ConcurrentLinkedQueue<>();
        this.tasksCount = new AtomicInteger(0);
        this.threads = new ArrayList<>(threadCount);
        this.isClosed = true;

        this.startThreads(threadCount);
    }

    @Override
    public void execute(Runnable command) {
        if (this.isClosed) {
            this.tasksCount.incrementAndGet();
            this.tasksQueue.offer(command);
        }
    }

    @Override
    public void close() throws InterruptedException {
        this.isClosed = false;

        for (Thread thread: threads) {
            thread.join();
        }
    }
}
