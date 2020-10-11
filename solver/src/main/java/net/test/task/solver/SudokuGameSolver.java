package net.test.task.solver;

import net.test.task.validator.InputValidator;
import net.test.task.validator.model.ValidationResult;

public class SudokuGameSolver implements Solver {

    private Solver delegate;
    private InputValidator validator;

    public SudokuGameSolver(Solver delegate, InputValidator validator) {
        this.delegate = delegate;
        this.validator = validator;
    }

    public int[][] solve(Integer[][] gameInput) {
        ValidationResult inputValidationResult = validator.validate(gameInput);
        if (inputValidationResult.isNotValid()) {
            throw new IllegalArgumentException("Input is not valid for the Sudoku game: "
                    + inputValidationResult.getValidationFailureMessage());
        }
        return delegate.solve(gameInput);
    }
}
