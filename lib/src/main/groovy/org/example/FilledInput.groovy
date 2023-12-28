package org.example

import groovy.transform.Immutable

import org.example.base.InputSlot

@Immutable(knownImmutables = ["value"])
class FilledInput<T> implements InputSlot<T> {
   final T value
}