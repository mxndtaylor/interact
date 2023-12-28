package org.example.err;

public class InteractError extends Error {
    public InteractError() {
        super();
    }
    public InteractError(String message) {
        super(message);
    }
    public InteractError(String message, Throwable cause) {
        super(message, cause);
    }
    public InteractError(Throwable cause) {
        super(cause);
    }
    public InteractError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
