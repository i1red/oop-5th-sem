public class App {
    public static void main(String[] args)  {
        ClassLoader classLoader = new FromFileClassLoader();
        try {
            Class cls = classLoader.loadClass("Student");
            Utils.printClassInfo(cls, true);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
