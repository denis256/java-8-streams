package com.user.service;

public class TypeMissingException extends Exception {

    public TypeMissingException() {
        super();
    }

    public TypeMissingException(String message) {
        super(message);
    }

    public TypeMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeMissingException(Throwable cause) {
        super(cause);
    }
}
