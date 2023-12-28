package org.example

interface InteractProvider<F extends InputForm> {
    Mono<FilledForm> prompt(F form)
}
