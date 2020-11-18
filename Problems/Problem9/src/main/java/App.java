import java.util.concurrent.Phaser;

public class App {
    public static Runnable createPhasedTask(String taskName, CustomPhaser phaser, int waitingTime, int phaseCount) {
        return () -> {
            phaser.register();

            for (int i = 0; i < phaseCount - 1; ++i) {
                System.out.printf("%s is executing phase #%d\n", taskName, phaser.getPhase());

                phaser.arriveAndAwaitAdvance();
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            phaser.arriveAndDeregister();
            System.out.printf("%s arrived and deregistered\n", taskName);
        };
    }

    public static void main(String[] args) {
        var phaser = new CustomPhaser();
        new Thread(createPhasedTask("Task #1", phaser, 150, 5)).start();
        new Thread(createPhasedTask("Task #2", phaser, 250, 3)).start();

        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.printf("Phase #%d is finished\n", phaser.getPhase() - 1);

        new Thread(createPhasedTask("Task #3", phaser, 100, 2)).start();
        phaser.arriveAndAwaitAdvance();
        System.out.printf("Phase #%d is finished\n", phaser.getPhase() - 1);

        phaser.arriveAndAwaitAdvance();
        System.out.printf("Phase #%d is finished\n", phaser.getPhase() - 1);

        phaser.arriveAndDeregister();
        System.out.println("Main arrived and deregistered");
    }
}
