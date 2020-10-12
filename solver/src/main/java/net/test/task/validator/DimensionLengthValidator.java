package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

import static net.test.task.misc.Constants.DIMENSION_LENGTH;

public class DimensionLengthValidator implements InputValidator {

    private static final String VALIDATION_FAILURE_MESSAGE = "input matrix must be exactly "
            + DIMENSION_LENGTH + "x" + DIMENSION_LENGTH;
    private static final ValidationResult INVALID = new ValidationResult(false, VALIDATION_FAILURE_MESSAGE);

    @Override
    public ValidationResult validate(Integer[][] sudokuMatrix) {
        if (sudokuMatrix.length != DIMENSION_LENGTH) {
            return INVALID;
        }
        for (Integer[] row : sudokuMatrix) {
            if (row.length != DIMENSION_LENGTH) {
                return INVALID;
            }
        }
        return ValidationResult.VALID;
    }
}
