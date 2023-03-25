package model;

import java.util.Objects;

public class Student implements User{
    private String UserId;
    private String Password = "password";
    private String Email;
    private String Name;


    public String getUserId(){
        return UserId;
    }

    public String getPassword(){
        return Password;
    }

    public String getEmail(){
        return Email;
    }

    public String getName(){
        return Name;
    }

    public String getProject(){
        return Name;
    }

    public String getUserId(){
        return UserId;
    }

    public void setPassword(String password){
        this.Password = password;
    }

    public void setEmail(String email){
        this.Email = email;
    }

    public String setName(String name){
        this.Name = name;
    }


    @Override
    public void changePassword(String password){
        Password = password;
    }
    public boolean login(String userid, String password){
        return (Objects.equals(UserId, userid) && Objects.equals(Password, password));
    }



}
