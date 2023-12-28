package org.example

import groovy.transform.Immutable

@Immutable(knownImmutable = "value")
class FilledInput<T> extends Input<T> {
   final T value 
}