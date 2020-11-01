public class CustomReentrantLock {
    private Thread lockedBy;
    private int lockCount;
    private final Object monitor;

    public CustomReentrantLock() {
        this.lockedBy = null;
        this.lockCount = 0;
        this.monitor = new Object();
    }

    public void lock() {
        synchronized (this.monitor) {
            try {
                while (this.lockedBy != null && this.lockedBy != Thread.currentThread()) {
                    this.monitor.wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.lockedBy = Thread.currentThread();
            ++this.lockCount;
        }
    }

    public void unlock() {
        synchronized (this.monitor) {
            if (this.lockedBy == Thread.currentThread()) {
                --this.lockCount;

                if (this.lockCount == 0) {
                    this.lockedBy = null;
                    this.monitor.notify();
                }
            }
        }
    }
}
