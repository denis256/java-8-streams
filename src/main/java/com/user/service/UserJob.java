package com.user.service;

import com.user.model.User;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * User job with handling errors inside
 */

public abstract class UserJob implements Consumer<User> {

    private ErrorHandler errorHandler;

    public UserJob errorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        return this;
    }

    public abstract void process(User user) throws Exception;

    public void accept(User user) {
        try {
            process(user);
        } catch (Exception e) {
            if (!errorHandler.handle(e)) {
                throw new RuntimeException(e);
            }
        }
    }
}
