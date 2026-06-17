package com.otimizza.teste.domain.defaults;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Default provider that sets a value if it's currently null.
 */
public class NullDefaultProvider<T, V> extends DefaultProvider<T> {
    private final Function<T, V> getter;
    private final BiConsumer<T, V> setter;
    private final Function<T, V> defaultValueSupplier;

    public NullDefaultProvider(Function<T, V> getter, BiConsumer<T, V> setter, Function<T, V> defaultValueSupplier) {
        this.getter = getter;
        this.setter = setter;
        this.defaultValueSupplier = defaultValueSupplier;
    }

    @Override
    public void provide(T target) {
        if (getter.apply(target) == null) {
            setter.accept(target, defaultValueSupplier.apply(target));
        }
        next(target);
    }
}
