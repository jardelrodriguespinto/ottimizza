package com.otimizza.teste.domain.validators.common;

import com.otimizza.teste.domain.validators.Validator;
import java.util.function.Function;

/**
 * Validator that checks if a field is not null.
 */
public class NotNullValidator<T> extends Validator<T> {
    private final Function<T, ?> fieldExtractor;
    private final String errorMessage;

    public NotNullValidator(Function<T, ?> fieldExtractor, String errorMessage) {
        this.fieldExtractor = fieldExtractor;
        this.errorMessage = errorMessage;
    }

    @Override
    public void validate(T target) {
        if (fieldExtractor.apply(target) == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        checkNext(target);
    }
}
