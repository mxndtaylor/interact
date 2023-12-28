package org.example

import groovy.transform.Immutable

@Immutable
class FilledForm {
    final ImmutableMap<String, FilledInput> filledInputs   

    <T> T get(Input<T> input) {
        return (filledInputs.get(input.name) as FilledInput<T>).value
    }

    Map<String, Object> getAll() {
        Map<String, Object> result = [:]

        filledInputs.each { Map.Entry<String, FilledInput> it ->
            result.put(it.key, it.value.value)
        }

        return result.asImmutable()
    }
    
    <T> T applyToCanonical(Class<T> canonicalClass) {
        canonicalClass.getConstructor(Map<String, Object>).apply(
            // TODO: process @Canonical, @Immutable, or that MapConstructor annotation
            //  could make this more reliable and less error-prone
            getAll().subMap(canonicalClass.fields*.name)
        )
    }
    
    static class Builder {
        private Map<String, FilledInput> filledInputs
        Builder() {}

        <T> Builder fill(String name, Input<T> input, T value) {
            filledInputs.put(name, input.fill(value))
        }

        FilledForm build() {
            new FilledForm(filledInputs: filledInputs.asImmutable())
        }
    }
}