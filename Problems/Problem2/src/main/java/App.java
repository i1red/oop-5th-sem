import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AbstractTriDiagonalSystemSolver solver = new ThomasSystemSolver();

        List<Double> upper = Arrays.asList(1., 1., -1.);
        List<Double> central = Arrays.asList(10., 9., 4., 8.);
        List<Double> lower = Arrays.asList(-2., 0.1, -1.);
        List<Double> constant = Arrays.asList(5., -1., -5., 40.);

        ArrayList<Double> result = solver.solve(upper, central, lower, constant);

        System.out.println(result);
        System.out.println(Utils.multiplyTriDiagonal(upper, central, lower, result));
        System.out.println(constant);
    }
}
