package net.test.task.solver;

import net.test.task.solver.model.Cell;
import net.test.task.solver.model.MissingCell;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.type.context.NumberContext;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinearProgrammingBasedSolver implements Solver {

    private static final NumberContext SOLUTION_CONTEXT = new NumberContext(0, RoundingMode.HALF_DOWN);

    @Override
    public int[][] solve(Integer[][] gameInput) {
        ExpressionsBasedModel model = new ExpressionsBasedModel();
        model.options.solution = SOLUTION_CONTEXT;
        Map<Cell, Map<Integer, Variable>> cellVariables = new HashMap<>();

        Variable[][][] oneZeroMatrix = new Variable[9][9][9];
        List<MissingCell> missingCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //MissingCell missingCell = new MissingCell(i, j);
                Cell cell = new Cell(i, j);
                final Integer cellValue = gameInput[i][j];
                /*if (cellValue == null) {
                    missingCells.add(missingCell);
                }*/
                for (int number = 1; number < 10; number++) {
                    Variable var = Variable.makeBinary(i + "_" + j + "_" + number);
                    if (cellValue != null) {
                        if (cellValue != number) {
                            var.level(0);
                        } else {
                            var.level(1);
                        }
                    }
                    cellVariables.computeIfAbsent(cell, c -> new HashMap<>()).put(number, var);
                    oneZeroMatrix[i][j][number - 1] = var;
                    model.addVariable(var);
                }
            }
        }

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
        System.out.println(result);

        if (result.getState().isFailure()) {
            return new int[][] {};
        }

        System.out.println(model);

        int[][] solution = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //refactor
                int value = 0;
                if (gameInput[i][j] == null) {
                    for (Map.Entry<Integer, Variable> variable : cellVariables.get(new Cell(i, j)).entrySet()) {
                        if (variable.getValue().getValue().intValue() == 1) {
                            value = variable.getKey();
                        }
                    }
                } else {
                    value = gameInput[i][j];
                }
                solution[i][j] = value;
            }
        }
        missingCells.forEach(missingCell -> {
            StringBuilder builder = new StringBuilder();
            builder.append("Row: ");
            builder.append(missingCell.getRow());
            builder.append(", column: ");
            builder.append(missingCell.getColumn());
            builder.append(", value: ");
            missingCell.getPossibleValues().forEach((number, var) -> {
                if (var.getValue().intValue() == 1) {
                    builder.append(number);
                    builder.append("\n");
                }
            });
            System.out.println(builder.toString());
        });

        return solution;
    }
}
