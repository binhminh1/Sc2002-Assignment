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





    public boolean sendChangeTitleRequest(String projectID, String newTitle, String requestId) {
        Request request = new Request(RequestType.changeTitle, projectID, this.userId, newTitle);
        requestId = request.getRequestId();
        return true;
    }

    public boolean sendSelectProjectRequest(String projectID, String studentID, String requestId){
        Request request = new Request(RequestType.assignProject, projectID, studentID);
        requestId = request.getRequestId();
        return true;
    }

    public boolean deregisterProjectRequest(String projectID, String studentID, String requestId){
        Request request = new Request(RequestType.deregister, projectID, studentID);
        requestId = request.getRequestId();
        return true;
    }

}
