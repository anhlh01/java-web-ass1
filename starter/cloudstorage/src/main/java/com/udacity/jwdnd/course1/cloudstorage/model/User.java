package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private int userId;
    private String userName;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

    public User(String lastName, String firstName, String password, String salt, String userName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.salt = salt;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
