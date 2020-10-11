package net.test.task.solver;

import net.test.task.solver.model.Solution;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

import java.util.ArrayList;
import java.util.List;

public class LinearProgrammingBasedSolver implements Solver {

    @Override
    public List<Solution> solve(Integer[][] gameInput) {
        ExpressionsBasedModel model = new ExpressionsBasedModel();
        List<Solution> solutions = new ArrayList<>();
        Variable[][][] oneZeroMatrix = buildOneZeroMatrix(gameInput, model, solutions);

        // each cell has exactly one value
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Expression expression = model.addExpression();
                expression.level(1);
                for (int k = 0; k < 9; k++) {
                    final Variable var = oneZeroMatrix[i][j][k];
                    expression.set(var, 1);
                }
                System.out.println(expression.getLinearEntrySet());
            }
        }

        // each row has only distinct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Expression expression = model.addExpression();
                expression.level(1);
                for (int k = 0; k < 9; k++) {
                    final Variable var = oneZeroMatrix[i][k][j];
                    expression.set(var, 1);
                }
            }
        }

        // each region has only distinct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j += 3) {
                for (int k = 0; k < 9; k += 3) {
                    Expression expression = model.addExpression();
                    expression.level(1);
                    for (int l = 0; l < 3; l++) {
                        for (int d = 0; d < 3; d++) {
                            final Variable var = oneZeroMatrix[j + l][k + d][i];
                            expression.set(var, 1);
                        }
                    }
                }
            }
        }

        // each column has only distinct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Expression expression = model.addExpression();
                expression.level(1);
                for (int k = 0; k < 9; k++) {
                    final Variable var = oneZeroMatrix[k][i][j];
                    expression.set(var, 1);
                }
            }
        }

        Optimisation.Result result = model.minimise();
        System.out.println(model);

        solutions.forEach(solution -> {
            StringBuilder builder = new StringBuilder();
            builder.append("Row: ");
            builder.append(solution.getRow());
            builder.append(", column: ");
            builder.append(solution.getColumn());
            builder.append(", value: ");
            solution.getVariables().forEach((number, var) -> {
                if (var.getValue().intValue() == 1) {
                    builder.append(number);
                    builder.append("\n");
                }
            });
            System.out.println(builder.toString());
        });
        return null;

    }

    private Variable[][][] buildOneZeroMatrix(Integer[][] source, ExpressionsBasedModel model, List<Solution> solutions) {
        Variable[][][] oneZeroMatrix = new Variable[9][9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Solution solution = new Solution(i, j);
                if (source[i][j] == null) {
                    solutions.add(solution);
                }
                for (int number = 1; number < 10; number++) {
                    Variable var = Variable.make(i + "_" + j + "_" + number);
                    if (source[i][j] != null) {
                        if (source[i][j] != number) {
                            var.level(0);
                        } else {
                            var.level(1);
                        }
                    } else {
                        var.binary();
                        solution.getVariables().put(number, var);
                    }
                    oneZeroMatrix[i][j][number - 1] = var;
                    model.addVariable(var);
                }
            }
        }
        return oneZeroMatrix;
    }
}
