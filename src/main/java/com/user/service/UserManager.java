package com.user.service;

import com.user.model.User;
import com.user.model.UserType;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class UserManager {

    public void remove(User user) {
        System.out.println("Removing user " + user);
    }

    public Boolean query(User user) {

        System.out.format("Starting Processing %s\n", user);
        Random random = new Random();
        try {
            Thread.sleep(200 *  (1 + random.nextInt(3)) );
        } catch (InterruptedException e) { }

        boolean result = random.nextBoolean();

        System.out.format("Ended Processing %s %s \n", user, result);
        return result;
    }

    public static Predicate<User>  IS_ACTIVE_ADMIN =  user -> {
        if (!user.isActive()) {
            return false;
        }
        return user.getType() == UserType.ADMIN;
    };

    public static Function<User, User> EMAIL_OBFUSCATOR = user -> { //new Function<User, User>()

        String email = user.getLogin();
        int atIndex = StringUtils.indexOf(email, "@");

        return User.of(user.getType(), StringUtils.substring(email, 0, 3)  + "***" + StringUtils.substring(email, atIndex, email.length()));
    };


    public static Predicate<User> INVALID_USER_FILTER = user -> {
        System.out.format("Checking %s\n", user);
        if (user == null) {
            return true;
        }

        if (StringUtils.isBlank(user.getLogin())) {
            return true;
        }
        if (user.getType() == null) {
            return true;
        }

        return false;
    };
}
