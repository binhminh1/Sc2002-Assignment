package model;

import repository.ProjectRepository;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
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

    public List<String> viewRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();

        for (Request request : RequestRepository.getRequests()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToId())
                    .append("\nStatus: ").append(request.getStatus());

            if (!request.getRequestHistory().isEmpty()) {
                sb.append("\nHistory:");
                for (RequestHistory history : request.getRequestHistory()) {
                    sb.append("\n- ").append(history.getStatus())
                            .append(" on ").append(history.getUpdatedDate());
                }
            }
            requestHistory.add(sb.toString());
        }
        return requestHistory;
    }
}
