package model;

import java.util.Objects;
import java.util.Scanner;


public class User {
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

    public Boolean login(String userId,Student student) {
//        while (true) {
            System.out.println("Enter your password: ");
            String password = sc.next();

            if (Objects.equals(this.password, student.getPassword())) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Wrong user ID or password. Please try again.");
                return false;
            }
//        }
    }

    public Boolean login(String userId, Supervisor supervisor) {
//        while (true) {
            System.out.println("Enter your password: ");
            String password = sc.next();

            if (Objects.equals(this.password, supervisor.getPassword())) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Wrong user ID or password. Please try again.");
                return false;
            }
//        }
    }

    public boolean login(String userId, Coordinator coordinator) {
            System.out.println("Enter your password: ");
            String password = sc.next();

            if (Objects.equals(this.password, coordinator.getPassword())) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Wrong user ID or password. Please try again.");
                return false;
            }
    }


}
