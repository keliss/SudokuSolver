package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

public class DimensionLengthValidator implements InputValidator {

    private static final int EXPECTED_DIMENSION_LENGTH = 9;
    private static final String INVALIDATION_FAILURE_MESSAGE = "game input must be exactly 9x9";
    private static final ValidationResult INVALID = new ValidationResult(false, INVALIDATION_FAILURE_MESSAGE);

    @Override
    public ValidationResult validate(Integer[][] gameInput) {
        if (gameInput.length != EXPECTED_DIMENSION_LENGTH) {
            return INVALID;
        }
        for (Integer[] row : gameInput) {
            if (row.length != EXPECTED_DIMENSION_LENGTH) {
                return INVALID;
            }
        }
        return ValidationResult.VALID;
    }
}
