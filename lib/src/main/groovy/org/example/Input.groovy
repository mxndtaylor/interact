package org.example

import groovy.transform.Immutable

@Immutable
class Input<T> {
    String name
    Class<T> type

}