package com.otimizza.teste.domain.validators;

/**
 * Base class for the Chain of Responsibility validation pattern.
 * @param <T> The type of object to validate.
 */
public abstract class Validator<T> {
    private Validator<T> next;

    /**
     * Links this validator with the next one in the chain.
     * @param next The next validator.
     * @return The next validator to allow chaining.
     */
    public Validator<T> linkWith(Validator<T> next) {
        this.next = next;
        return next;
    }

    /**
     * Validates the target object.
     * @param target The object to validate.
     * @throws IllegalArgumentException if validation fails.
     */
    public abstract void validate(T target);

    /**
     * Calls the next validator in the chain if it exists.
     * @param target The object to validate.
     */
    protected void checkNext(T target) {
        if (next != null) {
            next.validate(target);
        }
    }
}
