package com.user.model;


public class User {

    private UserType type;
    private String login;

    public static User of(UserType type, String login) {
        return new User(type, login);
    }

    public User() {
    }

    public User(UserType type, String login) {
        this.type = type;
        this.login = login;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("type=").append(type);
        sb.append(", login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
