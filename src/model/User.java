package model;

import java.util.Objects;
import java.util.Scanner;


public abstract class User {
    /**
     * Declare private variables
     */
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

    /**
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * @return ID of user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * set user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * update to new password
     */
    public void changePassword(String password) {
        this.password = password;
    }

    public abstract Boolean login();

    public abstract void ChangePassword();

    public abstract void viewProject();

}

