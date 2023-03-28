package model;

import java.util.Objects;

public class Student extends User{
    private StudentStatus status;
    public Student(String userId, String name, String email) {
        super(userId, name, email);
        this.status = StudentStatus.UNREGISTERED;
    }
    public void changeStatus(StudentStatus status){
        this.status = status;
    }

    public boolean sendChangeTitleRequest(String projectID, String newTitle, String requestId) {
        Request request = new Request(RequestType.changeTitle, projectID,super.getUserId(), newTitle);
        requestId = request.getRequestId();
        return true;
    }
    public boolean sendSelectProjectRequest(String projectID, String studentID, String requestId){
        Request request = new Request(RequestType.assignProject, projectID, studentID);
        requestId = request.getRequestId();
        return true;
    }
    public boolean sendDeregisterProjectRequest(String projectID, String studentID, String requestId){
        Request request = new Request(RequestType.deregister, projectID, studentID);
        requestId = request.getRequestId();
        return true;
    }
}
