package com.user.test;


import com.user.model.User;
import com.user.model.UserType;
import com.user.service.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.stream.Stream;

public class ErrorHandling {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Stream<User> users;

    @BeforeEach
    public void init() {
        users = Stream.of(
                User.of(UserType.ADMIN, "admin1@admin.com"),
                User.of(UserType.ADMIN, null),
                User.of(null, "admin2@admin2.com"),

                User.of(UserType.USER, "user@user.com"),
                User.of(null, "user@user.com")
        );
    }

    @Test
    public void findInvalid() {
        users.filter(UserManager.INVALID_USER_FILTER).findFirst().ifPresent(user -> {
            System.err.format("Invalid user %s", user);
           // throw new RuntimeException("Invalid user " + user);
        });

        //users.filter(UserManager.INVALID_USER_FILTER);
    }

    @Test
    public void processUsers1() {

        SimpleUserJob userJob = user -> {
            if (StringUtils.isBlank(user.getLogin())) {
                throw new LoginMissingException("Missing login for " + user);
            }
            System.out.format("Login : %s, length: %s\n", user.getLogin(), user.getLogin().length());
        };



        users.forEach( user -> {
            try {
                userJob.process(user);
            } catch (Exception e) {
                e.printStackTrace(); // exception handling

            }
        });
    }

    @Test
    public void processUsers2() {

        ErrorHandler loginMissingHandler = e -> {
            if (e instanceof LoginMissingException) {
                System.out.format("Handled exception " + e);
                return true;
            }
            System.err.format("Unknown exception " + e);
            return false;
        };

        UserJob userJob = new UserJob() {
            @Override
            public void process(User user) throws Exception {
                if (StringUtils.isBlank(user.getLogin())) {
                    throw new LoginMissingException("Missing login for " + user);
                }
                System.out.format("Login : %s, length: %s\n", user.getLogin(), user.getLogin().length());
            }
        }.errorHandler(loginMissingHandler);

        users.forEach(userJob);
    }
}
