package net.test.task.solver;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.type.context.NumberContext;

import java.math.RoundingMode;

import static net.test.task.misc.Constants.DIMENSION_LENGTH;

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

        Variable[][][] numberPickedFlagMatrix = buildNumberPickedFlagMatrix(model, sudokuMatrix);

        addConstraintsOnNumberOfDistinctValuesForAllDimensions(model, numberPickedFlagMatrix);
        addConstraintsOnDistinctValuesInRegion(model, numberPickedFlagMatrix);

        return calculateSolution(model, numberPickedFlagMatrix);
    }

    private Variable[][][] buildNumberPickedFlagMatrix(ExpressionsBasedModel model, Integer[][] source) {
        Variable[][][] numberPickedFlagMatrix = new Variable[DIMENSION_LENGTH][DIMENSION_LENGTH][DIMENSION_LENGTH];
        for (int row = 0; row < DIMENSION_LENGTH; row++) {
            for (int column = 0; column < DIMENSION_LENGTH; column++) {
                final Integer cellValue = source[row][column];
                for (int number = 0; number < DIMENSION_LENGTH; number++) {
                    Variable numberPickedFlag =
                            Variable.makeBinary(row + DIMENSION_SEPARATOR + column + DIMENSION_SEPARATOR + number);
                    if (cellValue != null) {
                        if (cellValue != number + 1) {
                            numberPickedFlag.level(0);
                        } else {
                            numberPickedFlag.level(1);
                        }
                    }
                    numberPickedFlagMatrix[row][column][number] = numberPickedFlag;
                    model.addVariable(numberPickedFlag);
                }
            }
        }
        return numberPickedFlagMatrix;
    }

    private void addConstraintsOnNumberOfDistinctValuesForAllDimensions(ExpressionsBasedModel model,
                                                                        Variable[][][] numberPickedFlagMatrix) {
        for (int i = 0; i < DIMENSION_LENGTH; i++) {
            for (int j = 0; j < DIMENSION_LENGTH; j++) {
                Expression numberOfValuesInSingleCellConstraint = model.addExpression();
                numberOfValuesInSingleCellConstraint.level(1);

                Expression distinctValuesInRowConstraint = model.addExpression();
                distinctValuesInRowConstraint.level(1);

                Expression distinctValuesInColumnConstraint = model.addExpression();
                distinctValuesInColumnConstraint.level(1);

                for (int k = 0; k < DIMENSION_LENGTH; k++) {
                    final Variable numberTraversalVariable = numberPickedFlagMatrix[i][j][k];
                    numberOfValuesInSingleCellConstraint.set(numberTraversalVariable, 1);

                    final Variable rowTraversalVariable = numberPickedFlagMatrix[i][k][j];
                    distinctValuesInRowConstraint.set(rowTraversalVariable, 1);

                    final Variable columnTraversalVariable = numberPickedFlagMatrix[k][j][i];
                    distinctValuesInColumnConstraint.set(columnTraversalVariable, 1);
                }
            }
        }
    }

    private void addConstraintsOnDistinctValuesInRegion(ExpressionsBasedModel model, Variable[][][] numberPickedFlagMatrix) {
        for (int number = 0; number < DIMENSION_LENGTH; number++) {
            for (int rowBase = 0; rowBase < DIMENSION_LENGTH; rowBase += 3) {
                for (int columnBase = 0; columnBase < DIMENSION_LENGTH; columnBase += 3) {
                    Expression distinctValuesInRegionConstraint = model.addExpression();
                    distinctValuesInRegionConstraint.level(1);
                    for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
                        for (int columnOffset = 0; columnOffset < 3; columnOffset++) {
                            final Variable regionTraversalVariable =
                                    numberPickedFlagMatrix[rowBase + rowOffset][columnBase + columnOffset][number];
                            distinctValuesInRegionConstraint.set(regionTraversalVariable, 1);
                        }
                    }
                }
            }
        }
    }

    private int[][] calculateSolution(ExpressionsBasedModel model, Variable[][][] numberPickedFlagMatrix) {
        Optimisation.Result result = model.minimise();

        if (result.getState().isFailure()) {
            return new int[][] {};
        }

        int[][] solution = new int[DIMENSION_LENGTH][DIMENSION_LENGTH];

        for (int row = 0; row < DIMENSION_LENGTH; row++) {
            for (int column = 0; column < DIMENSION_LENGTH; column++) {
                for (int number = 0; number < DIMENSION_LENGTH; number++) {
                    if (numberPickedFlagMatrix[row][column][number].getValue().intValue() == 1) {
                        solution[row][column] = number + 1;
                        break;
                    }
                }
                if (solution[row][column] == 0) {
                    throw new IllegalStateException("Couldn't find solution for cell: (" + row + ", " + column + ")");
                }
            }
        }

        return solution;
    }
}
