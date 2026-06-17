package com.otimizza.teste.domain.defaults;

/**
 * Base class for the Chain of Responsibility pattern for default values.
 * @param <T> The type of object being initialized.
 */
public abstract class DefaultProvider<T> {
    private DefaultProvider<T> next;

    /**
     * Links this provider with the next one in the chain.
     * @param next The next provider.
     * @return The next provider to allow chaining.
     */
    public DefaultProvider<T> linkWith(DefaultProvider<T> next) {
        this.next = next;
        return next;
    }

    /**
     * Provides default values for the target object.
     * @param target The object to initialize.
     */
    public abstract void provide(T target);

    /**
     * Calls the next provider in the chain if it exists.
     * @param target The object to initialize.
     */
    protected void next(T target) {
        if (next != null) {
            next.provide(target);
        }
    }
}
