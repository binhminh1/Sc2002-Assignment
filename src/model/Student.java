package model;

<<<<<<< Updated upstream
import repository.ProjectRepository;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> Stashed changes
import java.util.Objects;
/**
 * Makes use of inheritance and extends from User Class 
 */
public class Student extends User{
    /**
     * initialize private variables to be used in class
     */
    private StudentStatus status;
    public Student(String userId, String name, String email) {
        super(userId, name, email);
        this.status = StudentStatus.UNREGISTERED;
    }
<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
    public void changeStatus(StudentStatus status){
        this.status = status;
    }
    public StudentStatus getStatus(){
        return status;
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

    public List<String> viewOutgoingRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();
    
        for (Request request : RequestRepository.getRequestsByFromId(this.getUserId())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nProject ID: ").append(request.getProjectId())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToId())
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
}
