package org.example.err;

public class InteractException extends Exception {
    public InteractException() {
        super();
    }
    public InteractException(String message) {
        super(message);
    }
    public InteractException(String message, Throwable cause) {
        super(message, cause);
    }
    public InteractException(Throwable cause) {
        super(cause);
    }
    public InteractException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
