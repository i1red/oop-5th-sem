import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ParallelThomasSystemSolver extends AbstractTriDiagonalSystemSolver {
    Double[] modifiedLowerDiagonal;
    Double[] modifiedUpperDiagonal;
    Double[] modifiedConstantTermsLeft;
    Double[] modifiedConstantTermsRight;
    Double[] result;
    CyclicBarrier barrier;

    @Override
    protected Double[] noCheckSolveArrays(Double[] upperDiagonal, Double[] centralDiagonal,
                                        Double[] lowerDiagonal, Double[] constantTerms) {
        result = new Double[constantTerms.length];

        barrier = new CyclicBarrier(2);
        var topBottomThread = new Thread(
                rightRun(upperDiagonal, centralDiagonal, lowerDiagonal, constantTerms)
        );
        var bottomTopThread = new Thread(
                leftRun(upperDiagonal, centralDiagonal, lowerDiagonal, constantTerms)
        );

        topBottomThread.start();
        bottomTopThread.start();

        try {
            topBottomThread.join();
            bottomTopThread.join();
        } catch (InterruptedException e) {
            return null;
        }

        return result;
    }

    private Runnable rightRun(Double[] upperDiagonal, Double[] centralDiagonal,
                              Double[] lowerDiagonal, Double[] constantTerms) {
        return () -> {
            modifiedUpperDiagonal = new Double[upperDiagonal.length];
            modifiedConstantTermsLeft = new Double[constantTerms.length];

            modifiedUpperDiagonal[0] = -upperDiagonal[0] / centralDiagonal[0];
            modifiedConstantTermsLeft[0] = constantTerms[0] / centralDiagonal[0];

            double currentDivisor;
            int i = 1;
            for (; i < centralDiagonal.length / 2; ++i) {
                currentDivisor = centralDiagonal[i] + modifiedUpperDiagonal[i - 1] * lowerDiagonal[i - 1];

                modifiedUpperDiagonal[i] = -upperDiagonal[i] / currentDivisor;
                modifiedConstantTermsLeft[i] = (constantTerms[i] - modifiedConstantTermsLeft[i - 1] * lowerDiagonal[i - 1]) / currentDivisor;
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }

            --i;

            result[i] = (modifiedConstantTermsLeft[i] + modifiedUpperDiagonal[i] * modifiedConstantTermsRight[i + 1]) /
                    (1 - modifiedLowerDiagonal[i] * modifiedUpperDiagonal[i]);
            --i;

            for (; i >= 0; --i) {
                result[i] = modifiedUpperDiagonal[i] * result[i + 1] + modifiedConstantTermsLeft[i];
            }
        };
    }

    private Runnable leftRun(Double[] upperDiagonal, Double[] centralDiagonal,
                             Double[] lowerDiagonal, Double[] constantTerms) {
        return () -> {
            modifiedLowerDiagonal = new Double[lowerDiagonal.length];
            modifiedConstantTermsRight = new Double[constantTerms.length];

            int n = modifiedLowerDiagonal.length;

            modifiedLowerDiagonal[n - 1] = -lowerDiagonal[n - 1] / centralDiagonal[n];
            modifiedConstantTermsRight[n] = constantTerms[n] / centralDiagonal[n];

            double currentDivisor;
            int i = n - 1;
            for (; i >= centralDiagonal.length / 2; --i) {
                currentDivisor = centralDiagonal[i] + modifiedLowerDiagonal[i] * upperDiagonal[i];

                modifiedLowerDiagonal[i - 1] = -lowerDiagonal[i - 1] / currentDivisor;
                modifiedConstantTermsRight[i] = (constantTerms[i] - modifiedConstantTermsRight[i + 1] * upperDiagonal[i]) / currentDivisor;
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            ++i;

            result[i] = (modifiedConstantTermsRight[i] + modifiedLowerDiagonal[i - 1] * modifiedConstantTermsLeft[i - 1]) /
                    (1 - modifiedLowerDiagonal[i - 1] * modifiedUpperDiagonal[i - 1]);
            ++i;

            for (; i < result.length; ++i) {
                result[i] = modifiedLowerDiagonal[i - 1] * result[i - 1] + modifiedConstantTermsRight[i];
            }
        };
    }
}
