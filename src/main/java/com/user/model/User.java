package com.user.model;


public class User {

    private UserType type;
    private String login;
    private Boolean active = true;

    public static User of(UserType type, String login) {
        return User.of(type, login, true);
    }

    public static User of(UserType type, String login, Boolean active) {
        return new User(type, login, active);
    }

    public User() {
    }

    public User(UserType type, String login, Boolean active) {
        this.type = type;
        this.login = login;
        this.active = active;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", login='" + login + '\'' +
                ", active=" + active +
                '}';
    }
}
