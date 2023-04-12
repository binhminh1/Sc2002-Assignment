package model;

import repository.ProjectRepository;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes use of inheritance and extends from User Class 
 */
public class Student extends User{
    /**
     * initialize private variables to be used in class
     */
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
        this.Superid = ProjectRepository.getByID(projectId).getSupervisorName();
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
    public Request sendSelectProjectRequest(String projectID){
        Request request = new Request(RequestType.assignProject, projectID, super.getUserId());
        RequestRepository.addRequest(request);
        ProjectRepository.getByID(projectID).setStatus(ProjectStatus.RESERVED);
        return request;
    }
    public Request sendDeregisterProjectRequest(String projectID){
        Request request = new Request(RequestType.deregister, projectID, super.getUserId());
        RequestRepository.addRequest(request);
        return request;
    }

    public List<String> viewOutgoingRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();
    
        for (Request request : RequestRepository.getRequestsByFromId(this.getUserId())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nProject ID: ").append(request.getProjectId())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToName())
                    .append("\nReplacement supervisor: ").append(request.getReplacementSupId())
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

    public  void viewRequestHistoryStudent(String studentID){
        for (Request request : RequestRepository.getRequestsByFromId(studentID)){
            System.out.println("Request ID: "+ request.getRequestId());
            System.out.println("Student ID: "+ request.getFromId());
            System.out.println("Request Type: "+ request.getType() );
            System.out.println("Request Status: "+request.getStatus());
            System.out.println(" ");
        }
    }
}
