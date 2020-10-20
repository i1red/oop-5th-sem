import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Utils {
    private static final double ORIGIN = 0.5;
    private static final double BOUND = 10;

    public static ArrayList<Double> generateRandomList(int size) {
        var random = new Random();
        return random.doubles(size, ORIGIN, BOUND).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Double> multiplyTriDiagonal(List<Double> upperDiagonal, List<Double> centralDiagonal,
                                                 List<Double> lowerDiagonal, List<Double> variablesValues) {
        var constantTerms = new ArrayList<Double>(variablesValues.size());

        constantTerms.add(centralDiagonal.get(0) * variablesValues.get(0) + upperDiagonal.get(0) * variablesValues.get(1));

        for (int i = 1; i < variablesValues.size() - 1; ++i) {
            constantTerms.add(
                    lowerDiagonal.get(i - 1) * variablesValues.get(i - 1) +
                    centralDiagonal.get(i) * variablesValues.get(i) +
                    upperDiagonal.get(i) * variablesValues.get(i + 1)
            );
        }

        constantTerms.add(
                lowerDiagonal.get(variablesValues.size() - 2) * variablesValues.get(variablesValues.size() - 2) +
                centralDiagonal.get(variablesValues.size() - 1) * variablesValues.get(variablesValues.size() - 1)
        );

        return constantTerms;
    }
}
