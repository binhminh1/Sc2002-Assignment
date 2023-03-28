package model;

import repository.ProjectRepository;

import java.util.Objects;

public class Student extends User{
    private StudentStatus status;
    private String toId;
    private String projectId;

    private String Superid;

    public Student(String userId, String name, String email) {
        super(userId, name, email);
        this.projectId = "NULL";
        this.Superid = "NULL";
        this.status = StudentStatus.UNREGISTERED;
    }

    public void changeStatus(StudentStatus status){
        this.status = status;
    }
    public void changeProjectId(String projectId){
        this.projectId = projectId;
        this.Superid = ProjectRepository.getByID(projectId).getSupervisorId();
    }
    public StudentStatus getStatus(){
        return status;
    }
    public String getProjectId(){
        return projectId;
    }

    public String getSuperid(){
        return Superid;
    }
    public boolean sendChangeTitleRequest(String projectID, String toId, String newTitle) {
        Request request = new Request(RequestType.changeTitle, projectID, super.getUserId(), toId, newTitle);

        return true;
    }
    public boolean sendSelectProjectRequest(String projectID, String studentID){
        Request request = new Request(RequestType.assignProject, projectID, studentID);

        return true;
    }
    public boolean sendDeregisterProjectRequest(String projectID, String studentID, String requestId){
        Request request = new Request(RequestType.deregister, projectID, studentID);
        requestId = request.getRequestId();
        return true;
    }
}
