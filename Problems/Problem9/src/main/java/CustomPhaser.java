import java.util.concurrent.atomic.AtomicInteger;

public class CustomPhaser {
    private volatile int parties;
    private volatile int partiesAwait;
    private volatile int phase;

    public CustomPhaser() {
        this(0);
    }

    public CustomPhaser(int parties) {
        this.parties = parties;
        this.partiesAwait = parties;
        this.phase = 0;
    }

    public synchronized int register() {
        ++this.parties;
        ++this.partiesAwait;
        return phase;
    }

    public synchronized int arrive() {
        --this.partiesAwait;

        if (this.partiesAwait == 0) {
            this.partiesAwait = this.parties;
            this.notifyAll();
        }

        return this.phase;
    }

    public int arriveAndAwaitAdvance() {
        int currentPhase = this.phase;
        synchronized (this) {
            --this.partiesAwait;

            while (this.partiesAwait > 0 && this.partiesAwait != this.parties) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.partiesAwait = this.parties;
            this.phase = currentPhase + 1;
            this.notifyAll();

            return this.phase;
        }
    }

    public synchronized int arriveAndDeregister() {
        --this.partiesAwait;
        --this.parties;
        if (this.partiesAwait == 0) {
            this.partiesAwait = this.parties;
            this.notifyAll();
        }

        return this.phase;
    }

    int getPhase() {
        return phase;
    }
}