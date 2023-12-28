package org.example.base.slot;

import org.example.base.Fillable;
import org.example.base.Filled;

public class Slot<T extends SlotResult> implements Fillable<T> {
    private final String name;
    private final Class<T> type;

    public Slot(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {

        return name;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public Filled<T> fill(T value) {
        return new FilledSlot<>(value);
    }

    public FilledSlot<T> castAndFill(SlotResult value) {
        return (FilledSlot<T>)fill(type.cast(value));
    }
    
    public boolean isAssignable(SlotResult value) {
        return type.isInstance(value);
    }

    public boolean isAssignable(Class<?> clazz) {
        return type.isAssignableFrom(clazz);
    }
}