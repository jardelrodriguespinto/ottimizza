package com.otimizza.teste.domain.validators.common;

import com.otimizza.teste.domain.validators.Validator;
import java.util.function.Function;

/**
 * Validator that checks if a String field is not null or blank.
 */
public class NotBlankValidator<T> extends Validator<T> {
    private final Function<T, String> fieldExtractor;
    private final String errorMessage;

    public NotBlankValidator(Function<T, String> fieldExtractor, String errorMessage) {
        this.fieldExtractor = fieldExtractor;
        this.errorMessage = errorMessage;
    }

    @Override
    public void validate(T target) {
        String value = fieldExtractor.apply(target);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
        checkNext(target);
    }
}
