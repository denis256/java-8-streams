package com.user.model;

public class FrontendUser {

    private String login;

    public static FrontendUser of(User user) {
        return new FrontendUser(user.getLogin().toLowerCase());
    }

    public FrontendUser() {}

    public FrontendUser(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "FrontendUser{" +
                "login='" + login + '\'' +
                '}';
    }
}
