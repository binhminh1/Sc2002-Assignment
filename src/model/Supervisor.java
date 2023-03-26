package model;

import java.util.Objects;
public class Supervisor extends User {

    public Supervisor(String userId, String name, String email) {
        super(userId, name, email);
        super.userId = userId;
        super.name = name;
        super.email = email;
        super.password = "password";
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

    public String getProject() {
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


    @Override
    public String getID() {
        return getUserId();
    }

    @Override
    public void changePassword(String password) {
        this.password = password;
    }

    public boolean login(String userid, String password) {
        return (Objects.equals(userId, userid) && Objects.equals(this.password, password));
    }
    public Request sendTransferStudentRequest(String studentId, String supervisorId, String ProjectId){
        Request request = new Request(RequestType.transferStudent, studentId, supervisorId, ProjectId);
        return request;
    }
}