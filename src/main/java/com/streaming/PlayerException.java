package com.streaming;

public class PlayerException extends Exception {
    public PlayerException() {
    }

    public PlayerException(Throwable cause) {
        super(cause);
    }

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
