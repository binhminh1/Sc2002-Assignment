package model;

import repository.ProjectRepository;
import repository.RequestRepository;

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
    public Request sendChangeTitleRequest(String projectID, String toId, String newTitle) {
        Request request = new Request(RequestType.changeTitle, projectID, super.getUserId(), toId, newTitle);
        RequestRepository.addRequest(request);
        return request;
    }
    public Request sendSelectProjectRequest(String projectID, String studentID){
        Request request = new Request(RequestType.assignProject, projectID, studentID);
        RequestRepository.addRequest(request);
        return request;
    }
    public Request sendDeregisterProjectRequest(String projectID, String studentID){
        Request request = new Request(RequestType.deregister, projectID, studentID);
        RequestRepository.addRequest(request);
        return request;
    }
}
