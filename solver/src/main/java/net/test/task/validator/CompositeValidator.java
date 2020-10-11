package net.test.task.validator;

import net.test.task.validator.model.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class CompositeValidator implements InputValidator {

    private List<InputValidator> delegates;

    private CompositeValidator(List<InputValidator> delegates) {
        this.delegates = delegates;
    }

    @Override
    public ValidationResult validate(Integer[][] gameInput) {
        return delegates.stream()
                .map(validator -> validator.validate(gameInput))
                .filter(ValidationResult::isNotValid)
                .findFirst()
                .orElse(ValidationResult.VALID);
    }

    public static class Builder {

        private List<InputValidator> delegates = new ArrayList<>();

        public Builder addValidator(InputValidator validator) {
            delegates.add(validator);
            return this;
        }

        public CompositeValidator build() {
            return new CompositeValidator(delegates);
        }
    }
}
