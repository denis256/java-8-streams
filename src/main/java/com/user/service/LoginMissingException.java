package com.user.service;


public class LoginMissingException extends Exception {

    public LoginMissingException() {
        super();
    }

    public LoginMissingException(String message) {
        super(message);
    }

    public LoginMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginMissingException(Throwable cause) {
        super(cause);
    }
}
