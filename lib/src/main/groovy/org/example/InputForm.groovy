package org.example

abstract class InputForm<P extends InteractProvider> {
    final String title
    final String instructions

    final Map<String, InputField> fields

    private final FilledForm.Builder filler

    InputForm(String title, String instructions, Map<String, InputField> fields) {
        this.title = title
        this.instructions = instructions
        this.fields = fields
        this.filler = new FilledForm.Builder()
    }

    <T> void fillInput(String fieldName, T value) {
        filler.fill(fieldName, fields.get(fieldName).input as Input<T>, value)
    }

    FilledForm getFilled() {
        return filler.build()
    }

    abstract void render(P provider)

    static class Builder<T extends InputForm> {
        private String title
        private String instructions
        private Map<String, InputFields> fields
        private Constructor<T> constructor
        Builder(Class<T> type) {
            constructor = type.getConstructor(String, String, Map<String, InputField>)
        }

        Builder title(String title) {
            this.title = title
            return this
        }
        Builder instructions(String instructions) {
            this.instructions = instructions
            return this
        }
        public <T> Builder field(String label, String help, Input<T> input) {
            fields.put(input.name, new InputField<T>(label, help, input))
            this
        }
        T build() {
            // apply is not the right call... dunno what is without internet
            return constructor.apply(this.title, this.instructions, this.fields)
        }
    }
}