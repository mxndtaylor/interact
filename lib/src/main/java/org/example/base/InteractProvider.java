package org.example.base;

import java.io.Serializable;

import reactor.core.publisher.Mono;

public interface InteractProvider<T extends Serializable> {
    Mono<Filled<T>> prompt(Fillable<T> fillable);
}
