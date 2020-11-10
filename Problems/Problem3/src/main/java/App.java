public class App {
    public static Runnable createFunctionWithSleepTime(int sleepTime) {
        return () -> {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("Group #1");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "Group #2");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "Group #3");

        new Thread(threadGroup1, createFunctionWithSleepTime(9000), "Thread #1").start();
        new Thread(threadGroup1, createFunctionWithSleepTime(9000), "Thread #2").start();
        new Thread(threadGroup2, createFunctionWithSleepTime(1000), "Thread #3").start();
        new Thread(threadGroup2, createFunctionWithSleepTime(3000), "Thread #4").start();
        new Thread(threadGroup2, createFunctionWithSleepTime(11000), "Thread #5").start();
        new Thread(threadGroup3, createFunctionWithSleepTime(7000), "Thread #6").start();

        ThreadGroupPrinter.printWithInterval(threadGroup1);
        ThreadGroupPrinter.printWithInterval(threadGroup2, ConsoleColors.GREEN_BOLD, ConsoleColors.BLACK);
    }
}
