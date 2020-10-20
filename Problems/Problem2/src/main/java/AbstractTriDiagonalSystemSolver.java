import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTriDiagonalSystemSolver {
    protected abstract ArrayList<Double> noCheckSolve(List<Double> upperDiagonal, List<Double> centralDiagonal,
                                                      List<Double> lowerDiagonal, List<Double> constantTerms);

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

        return this.noCheckSolve(upperDiagonal, centralDiagonal, lowerDiagonal, constantTerms);
    }
}
