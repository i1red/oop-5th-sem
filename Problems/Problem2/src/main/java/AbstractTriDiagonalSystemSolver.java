import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTriDiagonalSystemSolver {
    protected abstract Double[] noCheckSolveArrays(Double[] upperDiagonal, Double[] centralDiagonal,
                                        Double[] lowerDiagonal, Double[] constantTerms);

    public ArrayList<Double> solve(List<Double> upperDiagonal, List<Double> centralDiagonal,
                                   List<Double> lowerDiagonal, List<Double> constantTerms) throws IllegalArgumentException {
        if (
                upperDiagonal.size() != lowerDiagonal.size() ||
                upperDiagonal.size() + 1 != centralDiagonal.size() ||
                centralDiagonal.size() != constantTerms.size() ||
                upperDiagonal.size() == 0
        ) {
            throw new IllegalArgumentException();
        }

        return new ArrayList<>(Arrays.asList(this.noCheckSolveArrays(upperDiagonal.toArray(Double[]::new),
                centralDiagonal.toArray(Double[]::new), lowerDiagonal.toArray(Double[]::new), constantTerms.toArray(Double[]::new))));
    }
}
