package net.test.task.validator.model;

import lombok.Data;

@Data
public class ValidationResult {

    public static final ValidationResult VALID = new ValidationResult(true, null);

    private final boolean isValid;
    private final String validationFailureMessage;

    public ValidationResult(boolean isValid, String validationFailureMessage) {
        this.isValid = isValid;
        this.validationFailureMessage = validationFailureMessage;
    }

    public boolean isNotValid() {
        return !isValid;
    }
}
