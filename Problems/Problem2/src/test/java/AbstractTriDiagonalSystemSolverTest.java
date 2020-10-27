import org.junit.Assert;
import org.junit.Test;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class AbstractTriDiagonalSystemSolverTest {
    private static final int MATRIX_SIZE = 20;
    private static final double DELTA = 0.01;
    private final AbstractTriDiagonalSystemSolver solver;

    @Parameters
    public static List<AbstractTriDiagonalSystemSolver> createSolvers() {
        return Arrays.asList(new ThomasSystemSolver(), new ParallelThomasSystemSolver());
    }

    public AbstractTriDiagonalSystemSolverTest(AbstractTriDiagonalSystemSolver solver) {
        this.solver = solver;
    }

    @Test
    public void solve() {
        List<Double> expectedResult = Utils.generateRandomList(MATRIX_SIZE);

        List<Double> upperDiagonal = Utils.generateRandomList(MATRIX_SIZE - 1);
        List<Double> centralDiagonal = Utils.generateRandomList(MATRIX_SIZE);
        List<Double> lowerDiagonal = Utils.generateRandomList(MATRIX_SIZE - 1);
        ArrayList<Double> constantTerms = Utils.multiplyTriDiagonal(upperDiagonal, centralDiagonal, lowerDiagonal, expectedResult);

        ArrayList<Double> actualResult = solver.solve(upperDiagonal, centralDiagonal, lowerDiagonal, constantTerms);

        Assert.assertArrayEquals(expectedResult.stream().mapToDouble(Double::doubleValue).toArray(),
                actualResult.stream().mapToDouble(Double::doubleValue).toArray(), DELTA);
    }
}