package org.example.base;

import java.io.Serializable;

public interface Fillable<T extends Serializable> {
    Filled<T> fill(T value);
}