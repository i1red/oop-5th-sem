public class ThomasSystemSolver extends AbstractTriDiagonalSystemSolver {
    @Override
    protected Double[] noCheckSolveArrays(Double[] upperDiagonal, Double[] centralDiagonal,
                                             Double[] lowerDiagonal, Double[] constantTerms) {
        var modifiedUpperDiagonal = new Double[upperDiagonal.length];
        var modifiedConstantTerms = new Double[constantTerms.length];

        modifiedUpperDiagonal[0] = upperDiagonal[0] / centralDiagonal[0];
        modifiedConstantTerms[0] = constantTerms[0] / centralDiagonal[0];

        double currentDivisor;
        int i = 1;
        for (; i < modifiedUpperDiagonal.length; ++i) {
            currentDivisor = centralDiagonal[i] - modifiedUpperDiagonal[i - 1] * lowerDiagonal[i - 1];

            modifiedUpperDiagonal[i] = upperDiagonal[i] / currentDivisor;
            modifiedConstantTerms[i] = (constantTerms[i] - modifiedConstantTerms[i - 1] * lowerDiagonal[i - 1]) / currentDivisor;
        }

        // last result element equals last modified upper diagonal element
        var result = new Double[constantTerms.length];
        result[i] = (constantTerms[i] - modifiedConstantTerms[i - 1] * lowerDiagonal[i - 1]) /
                        (centralDiagonal[i] - modifiedUpperDiagonal[i - 1] * lowerDiagonal[i - 1]);

        --i;
        for (; i >= 0; --i) {
            result[i] = modifiedConstantTerms[i] - modifiedUpperDiagonal[i] * result[i + 1];
        }
        return result;
    }
}
