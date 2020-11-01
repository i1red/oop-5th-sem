import java.util.List;

public class App {
    private static final int MATRIX_SIZE = 1_000_000;

    public static void main(String[] args) {
        AbstractTriDiagonalSystemSolver thomasSystemSolver = new ThomasSystemSolver();
        AbstractTriDiagonalSystemSolver parallelThomasSystemSolver = new ParallelThomasSystemSolver();

        List<Double> upper = Utils.generateRandomList(MATRIX_SIZE - 1);
        List<Double> central = Utils.generateRandomList(MATRIX_SIZE);
        List<Double> lower = Utils.generateRandomList(MATRIX_SIZE - 1);

        List<Double> expectedResult = Utils.generateRandomList(MATRIX_SIZE);
        List<Double> constant = Utils.multiplyTriDiagonal(upper, central, lower, expectedResult);

        PerformanceTester.printTime(() -> thomasSystemSolver.solve(upper, central, lower, constant));
        PerformanceTester.printTime(() -> parallelThomasSystemSolver.solve(upper, central, lower, constant));
    }
}
