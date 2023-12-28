package org.example.gui



class SwingInteractProvider implements InteractProvider<SwingInputForm> {

    SwingBuilder provide() {
        return new SwingBuilder()
    }

    Mono<FilledForm> prompt(SwingInputForm form) {
        form.render(this)
    }
}