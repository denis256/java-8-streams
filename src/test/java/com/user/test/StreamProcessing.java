package com.user.test;


import com.user.model.FrontendUser;
import com.user.model.User;
import com.user.model.UserType;
import com.user.service.SimpleUserJob;
import com.user.service.UserManager;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamProcessing {

    private Stream<User> users;
    private UserManager userManager;

    @BeforeEach
    public void init() {
        users = Stream.of(
                User.of(UserType.ADMIN, "admin1@admin.com"),
                User.of(UserType.ADMIN, "AdmiN2@aDmin.com"),
                User.of(UserType.ADMIN, "POTATO@POTATO.com"),
                User.of(UserType.ADMIN, "ripadmin@admin.com", false),
                User.of(UserType.USER, "user@user.com"),
                User.of(UserType.USER, "user2@USER.CoM"),
                User.of(UserType.USER, "user3@USER.CoM", false)
        );
        userManager = new UserManager();
    }

    // Filter returns a new stream that contains some of the elements of the original

    @Test
    public void userFilteringAndPrinting() {
        // filtering users by type
        List<User> adminUsers = users.filter(user -> user.getType() == UserType.ADMIN).collect(Collectors.toList());
        adminUsers.forEach(System.out::println);
    }

    @Test
    public void userFilteringAndProcessing() {
        // filtering and processing filtered items
        users.filter(user -> {
            String login = user.getLogin();
            return user.getType() == UserType.USER &&  "user@user.com".equals(login);
        })
                .forEach(userManager::remove);
    }

    @Test
    public void filterActiveAdmins() {
        // count active admins
        Long count = users.filter(UserManager.IS_ACTIVE_ADMIN).collect(Collectors.counting());
        System.out.format("Active admin users %s \n", count);

        users.forEach(System.out::println);
    }

    @Test
    public void findUser() throws Exception {
        // find admin user, throw exception if not found
        Optional<User> adminUser = users.sorted(Comparator.comparing(User::getType))
                .filter(user -> user.getType() == UserType.ADMIN).findFirst();
        adminUser.ifPresent(System.out::println);
        adminUser.orElseThrow(Exception::new);
    }

    // Map transforms the stream elements into something else

    @Test
    public void mappingEmails() {
        // fetching logins into lower case
        users.map(user -> user.getLogin()).map(String::toLowerCase).forEach(System.out::println);
    }

    @Test
    public void formattingUsersForFrontend() {
        // transform users for frontend
        List<User> frontendUsers = users.map((User u) ->  {
            u.setActive(false);
            return u;
        }).collect(Collectors.toList());
        frontendUsers.forEach(System.out::println);
    }

    @Test
    public void formattingUsersForFrontend2() {
        users
                .map(UserManager.EMAIL_OBFUSCATOR)
                .map(FrontendUser::of)
                .forEach(System.out::println);
    }

    @Test
    public void queryEachActiveAdmin() {
        // paralel query
        Function<User, Boolean> userQuery = userManager::query;

        users
                .parallel()
                .filter(UserManager.IS_ACTIVE_ADMIN)
                .map(user -> {
                    return user.getLogin() + " : " + userQuery.apply(user);
                })
                .forEach(System.out::println);
    }

    //Reduce performs a reduction of the stream to a single element


    @Test
    public void countAdmins() {
        // .parallel()
        Integer adminCount = users.parallel().reduce(0, (count, user) -> {
            if (user.getType() == UserType.USER) {
                return count;
            }
            System.out.format("accumulating count=%s; user=%s\n", count, user);
            return count + 1;
        }, (count1, count2) -> {
            System.out.format("combine %s; user=%s\n", count1, count2);
            return count1 + count2;
        });


        System.out.format("Admin users: %d\n", adminCount);


    }

    @Test
    public void aggregateQueryResults() {
        // query remote service with different users and collect results
        ConcurrentHashMap<Boolean, Collection<User>> queryResults = users.parallel().map(user -> {
            return Pair.of(userManager.query(user), user); //  results pair --->
        }).reduce(
                new ConcurrentHashMap<Boolean, Collection<User>>(), // <--- result accumulator
                (accumulator, result) -> {  // <-- result extractor

                    if (!accumulator.containsKey(result.getKey())) {
                        accumulator.put(result.getKey(), new HashSet<>());
                    }
                    accumulator.get(result.getKey()).add(result.getValue());

                    return accumulator;
                },
                (map1, map2) -> {  // <-- combining
                    ConcurrentHashMap<Boolean, Collection<User>> combine = new ConcurrentHashMap<Boolean, Collection<User>>();

                    BiConsumer<Boolean, Collection<User>> extractor = (status, users) -> {
                        if (!combine.containsKey(status)) {
                            combine.put(status, new HashSet<>());
                        }
                        combine.get(status).addAll(users);

                    };

                    map1.forEach(extractor);
                    map2.forEach(extractor);

                    // flatMap
//                    map1.entrySet().stream().flatMap(entry -> {
//                       entry.getKey();
//                    });

                    return combine;
                }
        );

        System.out.format("Query results \n");

        queryResults.forEach( (status, users) -> {
            System.out.format(" %s ", status);
            users.forEach( user -> { System.out.format("   %s", user.getLogin()); });
            System.out.format("\n");
        });

    }



}
