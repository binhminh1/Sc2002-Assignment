package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class User {
    String userId;
    String password = "password";
    String email;
    String name;

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

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setName(String name) {
            this.name = name;
        }


    public String getID() {
        return getUserId();
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public boolean login(String userid, String password) {
        return (Objects.equals(userId, userid) && Objects.equals(this.password, password));
    }

}
