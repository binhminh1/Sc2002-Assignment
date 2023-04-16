package model;

import repository.ProjectRepository;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Allows requests to be generated
 */
public class Request {
    private RequestType type;
    private String projectId;
    private String fromId;
    private String toName;
    private String replacementSupName;
    private String newTitle;
    private String requestId;
    private RequestStatus status;
    private ArrayList<RequestHistory> requestHistory;

    private String time;

    /**
     * Declaration and assignment of variables for changeTitle
     */
    public Request(RequestType type, String projectId, String fromId, String toId, String newTitle){
        if(type == RequestType.changeTitle){
            this.type = type;
            this.projectId = projectId;
            this.fromId = fromId;
            this.toName = ProjectRepository.getByID(projectId).getSupervisorName(); //Get supervisor from project ID, then get his name
            this.newTitle = newTitle;
            this.status = RequestStatus.Pending;
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.requestId = String.valueOf(new Date().getTime()) ;
            this.time = df.format(day) ;
            this.requestHistory = new ArrayList<>();
        }
    }

    /**
     * Declaration and assignment of variables for transferStudent
     */
    public Request(RequestType type, String projectId, String fromId, String newSuperName){
        if(type == RequestType.transferStudent){
            this.type = type;
            this.projectId = projectId;
            this.fromId = fromId;
            this.toName = "Li Fang";
            this.replacementSupName = newSuperName;
            this.status = RequestStatus.Pending;
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.time = df.format(day) ;
            this.requestId = String.valueOf(new Date().getTime()) ;
            this.requestHistory = new ArrayList<>();
        }
    }

    /**
     * 
     * Declaration and assignment of variables for registering and deregistering student
     * @param type request type
     * @param projectId
     * @param studentId
     */
    public Request(RequestType type, String projectId, String studentId){
        this.fromId = studentId;
        this.toName = "Li Fang";
        this.type = type;
        this.projectId = projectId;
        this.status = RequestStatus.Pending;
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        this.requestId = String.valueOf(new Date().getTime()) ;
        this.time = df.format(day) ;
        this.requestHistory = new ArrayList<>();
    }

    /**
     *
     * @return request type
     */
    public RequestType getType() {
        return type;
    }

    /**
     * get request status (PENDING, APPROVED, REJECTED)
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * 
     * @return time of request sent
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @return toName 
     */
    public String getToName() {
        return toName;
    }

    /**
     * change status of request after approval/rejection
     * @param status request status (PENDING, APPROVED, REJECTED)
     */
    public void changeStatus(RequestStatus status){
        this.status = status;
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(day);
        RequestHistory history = new RequestHistory(status, formattedDate) ;
        requestHistory.add(history);
    }

    /**
     * @return project ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @return replacement supervisor name
     */
    public String getReplacementSupName() {
        return replacementSupName;
    }

    /**
     * @return from ID
     */
    public String getFromId() {
        return fromId;
    }

    /**
     * @return new project title
     */
    public String getNewTitle() {
        return newTitle;
    }

    /**
     * @return request ID
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @return request history to be printed out
     */
    public ArrayList<RequestHistory> getRequestHistory() {
        return requestHistory;
    }
}
