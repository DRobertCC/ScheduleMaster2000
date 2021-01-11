package com.codecool.web.model;

import java.util.Objects;

public class User extends AbstractModel {

    private String username;
    private String userpassword;
    private boolean isAdmin;


    public User(Integer id, String userName, String userPassword, boolean isAdmin) {
        super(id);
        this.username = userName;
        this.userpassword = userPassword;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
