package org.example.gui

class SwingInputForm extends InputForm<SwingInteractProvider> {
    SwingInputForm(String title, String instructions, 
                   Map<String, InputField<Object, SwingInteractProvider>> fields) {
        super(title, instructions, fields)
    }

    @Override
    void render(SwingInteractProvider provider) {
        SwingBuilder builder = provider.provide()

        builder.edt {
            dialog() {
                vbox {
                    label(title)
                    // render instructions as html?
                    label(instructions)
                    for (field in fields.values) {
                        field.render(builder)
                    }
                }
            }
        }
    }

    static Builder<SwingInputForm> getBuilder() {
        return new InputForm.Builder<SwingInputForm>(SwingInputForm.class)
    }
}