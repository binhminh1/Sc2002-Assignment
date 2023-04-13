package model;

import java.util.Objects;
import java.util.Scanner;


public abstract class User {
    private String userId;
    private String password = "password";
    private String email;
    private String name;
    Scanner sc = new Scanner(System.in);

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = "password";
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public abstract Boolean login();





    public abstract void ChangePassword();

}

