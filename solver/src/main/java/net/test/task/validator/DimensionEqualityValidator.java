package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

public class DimensionEqualityValidator implements InputValidator {

    private static final int EXPECTED_DIMENSION_LENGTH = 9;
    private static final String VALIDATION_FAILURE_MESSAGE = "game input must be exactly 9x9";
    private static final ValidationResult INVALID = new ValidationResult(false, VALIDATION_FAILURE_MESSAGE);

    @Override
    public ValidationResult validate(Integer[][] matrix) {
        if (matrix.length != EXPECTED_DIMENSION_LENGTH) {
            return INVALID;
        }
        for (Integer[] row : matrix) {
            if (row.length != EXPECTED_DIMENSION_LENGTH) {
                return INVALID;
            }
        }
        return ValidationResult.VALID;
    }
}
