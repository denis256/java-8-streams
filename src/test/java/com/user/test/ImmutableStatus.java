package com.user.test;


import com.user.model.User;
import com.user.model.UserType;
import com.user.service.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ImmutableStatus {

    private Stream<User> users;

    @BeforeEach
    public void init() {
        users = Stream.of(
                User.of(UserType.ADMIN, "admin1@admin.com"),
                User.of(UserType.ADMIN, "AdmiN2@aDmin.com"),
                User.of(UserType.ADMIN, "POTATO@POTATO.com"),
                User.of(UserType.ADMIN, "rip@admin.com", false),
                User.of(UserType.USER, "user@user.com"),
                User.of(UserType.USER, "user2@USER.CoM")
        );
    }

    @Test
    public void immutableProcessing() {

        UserType comparedType = UserType.USER;
        List<User> extractedList = new ArrayList<>();

        new Thread() {
            public void run() {
                extractedList.clear();;
            }
        }.start();;

       // extractedList = null;

//        users.forEach(user -> {
//            if (user.getType() == comparedType) {
//                extractedList.add(user);
//            }
//        });

        //omparedType = null;
        //userList = new ArrayList<>();
        extractedList.forEach(System.out::println);
    }

}
