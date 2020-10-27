import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AbstractTriDiagonalSystemSolver solver = new ParallelThomasSystemSolver();

        List<Double> upper = Utils.generateRandomList(8);
        List<Double> central = Utils.generateRandomList(9);
        List<Double> lower = Utils.generateRandomList(8);
        List<Double> constant = Utils.generateRandomList(9);

        ArrayList<Double> result = solver.solve(upper, central, lower, constant);

        System.out.println(result);
        System.out.println(Utils.multiplyTriDiagonal(upper, central, lower, result));
        System.out.println(constant);
    }
}
