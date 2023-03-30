package model;

import java.util.Objects;
public class Coordinator extends User{
    private String userId;
    private String password = "password";
    private String email;
    private String name;

    public Coordinator(String userId, String name, String email) {
        super(userId, name, email);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = "password";
    }


    
   



}
