package model;

import java.util.Objects;

public class Student extends User{
    public Student(String userId, String name, String email) {
        super(userId, name, email);
        super.userId = userId;
        super.name = name;
        super.email = email;
        super.password = "password";
    }


    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getProject(){
        return name;
    }

    public String getUserId(){
        return userId;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }


    @Override
    public String getID() {
        return getUserId();
    }

    @Override
    public void changePassword(String password){
        this.password = password;
    }
    public boolean login(String userid, String password){
        return (Objects.equals(userId, userid) && Objects.equals(this.password, password));
    }

    public Request sendChangeTitleRequest(String studentID, String projectID, String newTitle){
        Request request = new Request(RequestType.changeTitle, projectID, newTitle, studentID);
        return request;
    }
    public Request sendSelectProjectRequest(String projectID, String studentID){
        Request request = new Request(RequestType.assignProject, projectID, studentID);
        return request;
    }

    }
