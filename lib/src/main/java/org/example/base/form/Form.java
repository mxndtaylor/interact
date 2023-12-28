package org.example.base.form;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.example.base.Fillable;
import org.example.base.Filled;

public class Form<T extends FormResult<T>> implements Fillable<T> {
    private final Class<T> immutableType;
    private final T immutableBaseValue;
    private final FilledForm.Builder<T> builder;

    @SuppressWarnings("unchecked")
    public Form(T immutableBaseValue) {
        // the class of immutableBaseValue is what determines the actual value of T
        // so this should always be safe
        this((Class<T>)immutableBaseValue.getClass(), immutableBaseValue);
    }

    public Form(Class<T> immutableType) throws {
        this(immutableType, );
    }

    public Form(Class<T> immutableType, T immutableBaseValue) {
        this.immutableType = immutableType;
        if (immutableBaseValue == null) {
            immutableBaseValue = 
        }
        this.immutableBaseValue = immutableBaseValue;
        this.builder = new FilledForm.Builder<>(immutableBaseValue.getMeta());
    }

    public boolean hasBaseValue() {
        return immutableBaseValue != null;
    }

    public Class<T> getImmutableType() {
        return immutableType;
    }

    public T getImmutableBaseValue() {
        return immutableBaseValue;
    }

    @Override
    public Filled<T> fill(T value) {
        // TODO: use passed value to override the specified base values
        //  then use MapConstructor of the passed type to create the filled instance
        return builder.addFrom(immutableBaseValue).addFrom(value);
    }
}
