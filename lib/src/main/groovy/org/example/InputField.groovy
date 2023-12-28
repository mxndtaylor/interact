//package org.example
//
//@Canonical
//abstract class InputField<T, P extends InteractProvider> {
//    String label
//    String help
//    Input<T> input
//
//    FilledInput<T> fill(T value) {
//        return new FilledInput<>(
//                name: input.name,
//                type: input.type,
//                value: value
//        )
//    }
//
//    abstract void render(P provider)
//
//    abstract void fill()
//}