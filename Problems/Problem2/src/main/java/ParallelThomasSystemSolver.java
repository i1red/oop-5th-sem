import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ParallelThomasSystemSolver extends AbstractTriDiagonalSystemSolver {
    private Double[] upperDiagonal;
    private Double[] centralDiagonal;
    private Double[] lowerDiagonal;
    private Double[] constantTerms;
    private Double[] modifiedLowerDiagonal;
    private Double[] modifiedUpperDiagonal;
    private Double[] modifiedConstantTermsTop;
    private Double[] modifiedConstantTermsBottom;
    private Double[] result;
    CyclicBarrier barrier;
    int middle;

    @Override
    protected Double[] noCheckSolveArrays(Double[] upperDiagonal, Double[] centralDiagonal,
                                          Double[] lowerDiagonal, Double[] constantTerms) {
        this.reset(upperDiagonal, centralDiagonal, lowerDiagonal, constantTerms);
        var topBottomThread = new Thread(this::topRun);
        var bottomTopThread = new Thread(this::bottomRun);

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

    private void reset(Double[] upperDiagonal, Double[] centralDiagonal,
                       Double[] lowerDiagonal, Double[] constantTerms) {
        this.upperDiagonal = upperDiagonal;
        this.modifiedUpperDiagonal = new Double[upperDiagonal.length];
        this.centralDiagonal = centralDiagonal;
        this.lowerDiagonal = lowerDiagonal;
        this.modifiedLowerDiagonal = new Double[lowerDiagonal.length];
        this.constantTerms = constantTerms;
        this.modifiedConstantTermsTop = new Double[constantTerms.length];
        this.modifiedConstantTermsBottom = new Double[constantTerms.length];
        this.result = new Double[constantTerms.length];
        this.barrier = new CyclicBarrier(2);
        this.middle = this.centralDiagonal.length / 2;
    }

    private void waitForOtherThread() {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void topRun() {
        modifiedUpperDiagonal[0] = -upperDiagonal[0] / centralDiagonal[0];
        modifiedConstantTermsTop[0] = constantTerms[0] / centralDiagonal[0];

        double currentDivisor;
        for (int i = 1; i < middle; ++i) {
            currentDivisor = centralDiagonal[i] + modifiedUpperDiagonal[i - 1] * lowerDiagonal[i - 1];

            modifiedUpperDiagonal[i] = -upperDiagonal[i] / currentDivisor;
            modifiedConstantTermsTop[i] = (constantTerms[i] - modifiedConstantTermsTop[i - 1] * lowerDiagonal[i - 1]) / currentDivisor;
        }

        waitForOtherThread();

        result[middle - 1] = (modifiedConstantTermsTop[middle - 1] + modifiedUpperDiagonal[middle - 1] * modifiedConstantTermsBottom[middle]) /
                (1 - modifiedLowerDiagonal[middle - 1] * modifiedUpperDiagonal[middle - 1]);

        for (int i = middle - 2; i >= 0; --i) {
            result[i] = modifiedUpperDiagonal[i] * result[i + 1] + modifiedConstantTermsTop[i];
        }
    }

    private void bottomRun() {
        int end = centralDiagonal.length - 1;
        modifiedLowerDiagonal[end - 1] = -lowerDiagonal[end - 1] / centralDiagonal[end];
        modifiedConstantTermsBottom[end] = constantTerms[end] / centralDiagonal[end];

        double currentDivisor;
        for (int i = end - 1; i >= middle; --i) {
            currentDivisor = centralDiagonal[i] + modifiedLowerDiagonal[i] * upperDiagonal[i];

            modifiedLowerDiagonal[i - 1] = -lowerDiagonal[i - 1] / currentDivisor;
            modifiedConstantTermsBottom[i] = (constantTerms[i] - modifiedConstantTermsBottom[i + 1] * upperDiagonal[i]) / currentDivisor;
        }

        waitForOtherThread();

        result[middle] = (modifiedConstantTermsBottom[middle] + modifiedLowerDiagonal[middle - 1] * modifiedConstantTermsTop[middle - 1]) /
                (1 - modifiedLowerDiagonal[middle - 1] * modifiedUpperDiagonal[middle - 1]);

        for (int i = middle + 1; i < result.length; ++i) {
            result[i] = modifiedLowerDiagonal[i - 1] * result[i - 1] + modifiedConstantTermsBottom[i];
        }
    }
}
