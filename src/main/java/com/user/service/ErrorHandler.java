package com.user.service;

@FunctionalInterface
public interface ErrorHandler {
    boolean handle(Exception e);
}
