package com.user.test;


import com.user.model.User;
import com.user.model.UserType;
import com.user.service.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsersStreamTesting {

    private Collection<User> users;
    private UserManager userManager;

    @BeforeEach
    public void init() {
        users  = Arrays.asList(
                User.of(UserType.ADMIN, "admin@admin.com"),
                User.of(UserType.ADMIN, "admin2@admin.com"),
                User.of(UserType.USER, "user@user.com"),
                User.of(UserType.USER, "user2@user.com")
        );
        userManager = new UserManager();
    }

    @Test
    public void userFilteringAndPrinting() {
        // filtering users by type
        List<User> adminUsers = users.stream().filter(user -> user.getType() == UserType.ADMIN).collect(Collectors.toList());
        adminUsers.forEach(System.out::println);
    }

    @Test
    public void userFilteringAndProcessing() {
        // filtering and processing filtered items
        users.stream().filter(user -> user.getType() == UserType.USER).forEach(userManager::remove);
    }

}
