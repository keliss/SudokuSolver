package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

public interface InputValidator {

    ValidationResult validate(Integer[][] gameInput);
}
