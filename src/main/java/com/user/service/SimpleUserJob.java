package com.user.service;

import com.user.model.User;

import java.util.function.Function;


@FunctionalInterface
public interface SimpleUserJob {

    void process(User user) throws Exception;

}
