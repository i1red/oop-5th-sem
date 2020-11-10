public class ThreadGroupPrinter {
    public static final int DEFAULT_INTERVAL = 5000;

    public static void printWithInterval(ThreadGroup threadGroup) {
        printWithInterval(threadGroup, DEFAULT_INTERVAL);
    }

    public static void printWithInterval(ThreadGroup threadGroup, int interval) {
        printWithInterval(threadGroup, interval, ConsoleColors.RED_BOLD, ConsoleColors.BLACK);
    }

    public static void printWithInterval(ThreadGroup threadGroup, String groupPrintColor, String threadPrintColor) {
        printWithInterval(threadGroup, DEFAULT_INTERVAL, groupPrintColor, threadPrintColor);
    }

    public static void printWithInterval(ThreadGroup threadGroup, int interval, String groupPrintColor, String threadPrintColor){
        new Thread(() -> printWithIntervalImplementation(threadGroup, interval, groupPrintColor, threadPrintColor)).start();
    }

    private static void printWithIntervalImplementation(ThreadGroup threadGroup, int interval, String groupPrintColor, String threadPrintColor) {
        while(threadGroup.activeCount() > 0){
            print(threadGroup, 0, groupPrintColor, threadPrintColor);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized void print(ThreadGroup threadGroup, int nestingLevel, String groupPrintColor, String threadPrintColor) {
        printIndentedLine(threadGroup.getName(), nestingLevel, groupPrintColor);

        var threadsInCurrentGroup = new Thread[threadGroup.activeCount()];
        int threadsCount = threadGroup.enumerate(threadsInCurrentGroup, false);

        for (int i = 0; i < threadsCount; ++i) {
            printIndentedLine(threadsInCurrentGroup[i].getName(), nestingLevel + 1, threadPrintColor);
        }

        var threadGroups = new ThreadGroup[threadGroup.activeGroupCount()];
        int groupsCount = threadGroup.enumerate(threadGroups, false);

        for (int i = 0; i < groupsCount; ++i) {
            print(threadGroups[i], nestingLevel + 1, groupPrintColor, threadPrintColor);
        }

        if (groupsCount == 0) {
            System.out.println();
        }
    }

    private static void printIndentedLine(String name, int nestingLevel, String color) {
        System.out.printf("%s%s%s%s\n", " ".repeat(nestingLevel * 4), color, name, ConsoleColors.RESET);
    }
}
