package model;

import repository.ProjectRepository;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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

    // Deregister and register
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

    public RequestType getType() {
        return type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getToName() {
        return toName;
    }

    public void changeStatus(RequestStatus status){
        this.status = status;
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(day);
        RequestHistory history = new RequestHistory(status, formattedDate) ;
        requestHistory.add(history);
    }

    public String getProjectId() {
        return projectId;
    }

    public String getReplacementSupName() {
        return replacementSupName;
    }

    public String getFromId() {
        return fromId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getRequestId() {
        return requestId;
    }

    public ArrayList<RequestHistory> getRequestHistory() {
        return requestHistory;
    }
}
