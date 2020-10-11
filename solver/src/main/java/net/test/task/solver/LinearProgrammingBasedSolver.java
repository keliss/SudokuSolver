package net.test.task.solver;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.type.context.NumberContext;

import java.math.RoundingMode;

/**
 * This solver is based on the idea described in the following article:
 * https://www.researchgate.net/publication/228615106_An_integer_programming_model_for_the_sudoku_problem
 */
public class LinearProgrammingBasedSolver implements Solver {

    private static final NumberContext SOLUTION_CONTEXT = new NumberContext(0, RoundingMode.HALF_DOWN);
    private static final String DIMENSION_SEPARATOR = "_";

    @Override
    public int[][] solve(Integer[][] sudokuMatrix) {
        ExpressionsBasedModel model = new ExpressionsBasedModel();
        model.options.solution = SOLUTION_CONTEXT;

        Variable[][][] oneZeroMatrix = buildOneZeroMatrix(model, sudokuMatrix);

        addConstraintsOnNumberOfDistinctValuesInAllDimensions(model, oneZeroMatrix);
        addConstraintsOnDistinctValuesInRegion(model, oneZeroMatrix);

        return calculateSolution(model, oneZeroMatrix);
    }

    private Variable[][][] buildOneZeroMatrix(ExpressionsBasedModel model, Integer[][] source) {
        Variable[][][] oneZeroMatrix = new Variable[9][9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final Integer cellValue = source[i][j];
                for (int number = 0; number < 9; number++) {
                    Variable var = Variable.makeBinary(i + DIMENSION_SEPARATOR + j + DIMENSION_SEPARATOR + number);
                    if (cellValue != null) {
                        if (cellValue != number + 1) {
                            var.level(0);
                        } else {
                            var.level(1);
                        }
                    }
                    oneZeroMatrix[i][j][number] = var;
                    model.addVariable(var);
                }
            }
        }
        return oneZeroMatrix;
    }

    private void addConstraintsOnNumberOfDistinctValuesInAllDimensions(ExpressionsBasedModel model, Variable[][][] oneZeroMatrix) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Expression numberOfValuesInSingleCellConstraint = model.addExpression();
                numberOfValuesInSingleCellConstraint.level(1);
                Expression distinctValuesInRowConstraint = model.addExpression();
                distinctValuesInRowConstraint.level(1);
                Expression distinctValuesInColumnConstraint = model.addExpression();
                distinctValuesInColumnConstraint.level(1);
                for (int k = 0; k < 9; k++) {
                    final Variable numberTraversalVariable = oneZeroMatrix[i][j][k];
                    numberOfValuesInSingleCellConstraint.set(numberTraversalVariable, 1);
                    final Variable rowTraversalVariable = oneZeroMatrix[i][k][j];
                    distinctValuesInRowConstraint.set(rowTraversalVariable, 1);
                    final Variable columnTraversalVariable = oneZeroMatrix[k][j][i];
                    distinctValuesInColumnConstraint.set(columnTraversalVariable, 1);
                }
            }
        }
    }

    private void addConstraintsOnDistinctValuesInRegion(ExpressionsBasedModel model, Variable[][][] oneZeroMatrix) {
        for (int number = 0; number < 9; number++) {
            for (int rowBase = 0; rowBase < 9; rowBase += 3) {
                for (int columnBase = 0; columnBase < 9; columnBase += 3) {
                    Expression distinctValuesInRegionConstraint = model.addExpression();
                    distinctValuesInRegionConstraint.level(1);
                    for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
                        for (int columnOffset = 0; columnOffset < 3; columnOffset++) {
                            final Variable regionTraversalVariable = oneZeroMatrix[rowBase + rowOffset][columnBase + columnOffset][number];
                            distinctValuesInRegionConstraint.set(regionTraversalVariable, 1);
                        }
                    }
                }
            }
        }
    }

    private int[][] calculateSolution(ExpressionsBasedModel model, Variable[][][] oneZeroMatrix) {
        Optimisation.Result result = model.minimise();

        if (result.getState().isFailure()) {
            return new int[][] {};
        }

        int[][] solution = new int[9][9];

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                for (int number = 0; number < 9; number++) {
                    if (oneZeroMatrix[row][column][number].getValue().intValue() == 1) {
                        solution[row][column] = number + 1;
                    }
                }
            }
        }

        return solution;
    }
}
