import java.lang.reflect.*;
import java.util.Arrays;

public class Utils {
    private static String parametersToString(Parameter[] parameters) {
        return String.join(
                ", ",
                Arrays.stream(parameters).map(parameter -> parameter.getType().getSimpleName()).toArray(String[]::new)
        );
    }

    public static void printClassInfo(Class cls, boolean printSuper) {
        System.out.printf("Fields, constructors and methods of class %s: \n\n", cls.getName());

        for (Field field : cls.getDeclaredFields()) {
            System.out.printf(
                    "%s %s %s;\n",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName()
            );
        }

        if (cls.getDeclaredFields().length > 0) {
            System.out.println();
        }

        for (Constructor constructor: cls.getDeclaredConstructors()) {
            System.out.printf(
                    "%s %s(%s);\n",
                    Modifier.toString(constructor.getModifiers()),
                    constructor.getDeclaringClass().getSimpleName(),
                    parametersToString(constructor.getParameters())
            );
        }

        if (cls.getDeclaredConstructors().length > 0) {
            System.out.println();
        }

        for (Method method : cls.getDeclaredMethods()) {
            System.out.printf(
                    "%s %s %s(%s);\n",
                    Modifier.toString(method.getModifiers()),
                    method.getReturnType().getSimpleName(),
                    method.getName(),
                    parametersToString(method.getParameters())
            );
        }

        if (printSuper) {
            if (cls.getSuperclass() != null) {
                System.out.println();
                printClassInfo(cls.getSuperclass(), true);
            }
        }
    }
}