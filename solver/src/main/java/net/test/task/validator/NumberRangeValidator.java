package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

import static net.test.task.misc.Constants.LOWER_NUMBER_RANGE_BORDER;
import static net.test.task.misc.Constants.UPPER_NUMBER_RANGE_BORDER;

public class NumberRangeValidator implements InputValidator {

    private static final String VALIDATION_FAILURE_MESSAGE = "input matrix must only have numbers between "
            + LOWER_NUMBER_RANGE_BORDER + " and " + UPPER_NUMBER_RANGE_BORDER + ", inclusively";
    private static final ValidationResult INVALID = new ValidationResult(false, VALIDATION_FAILURE_MESSAGE);

    @Override
    public ValidationResult validate(Integer[][] sudokuMatrix) {
        for (Integer[] row : sudokuMatrix) {
            for (Integer number : row) {
                if (number != null && (number < LOWER_NUMBER_RANGE_BORDER || number > UPPER_NUMBER_RANGE_BORDER)) {
                    return INVALID;
                }
            }
        }
        return ValidationResult.VALID;
    }
}
