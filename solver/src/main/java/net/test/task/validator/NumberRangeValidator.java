package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

public class NumberRangeValidator implements InputValidator {

    private static final int LOWER_BORDER = 1;
    private static final int UPPER_BORDER = 9;
    private static final String VALIDATION_FAILURE_MESSAGE = "game input must only have numbers between 1 and 9, inclusively";
    private static final ValidationResult INVALID = new ValidationResult(false, VALIDATION_FAILURE_MESSAGE);

    @Override
    public ValidationResult validate(Integer[][] matrix) {
        for (Integer[] row : matrix) {
            for (Integer number : row) {
                if (number != null && (number < LOWER_BORDER || number > UPPER_BORDER)) {
                    return INVALID;
                }
            }
        }
        return ValidationResult.VALID;
    }
}
