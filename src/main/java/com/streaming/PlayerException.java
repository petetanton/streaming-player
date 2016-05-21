package com.streaming;

public class PlayerException extends Exception {

    public PlayerException(Throwable cause) {
        super(cause);
    }

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(String message, Throwable cause) {
        super(message, cause);
    }

}
