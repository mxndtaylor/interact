package org.example.base;

import java.io.Serializable;

public interface Filled<T extends Serializable> {
    T value();
}
