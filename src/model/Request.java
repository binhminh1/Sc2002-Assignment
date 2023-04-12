package model;

import repository.ProjectRepository;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Request {
    private RequestType type;
    private String projectId;
    private String fromId;
    private String toId;
    private String replacementSupId;
    private String newTitle;
    private String requestId;
    private RequestStatus status;
    private ArrayList<RequestHistory> requestHistory;


    public Request(RequestType type, String projectId, String fromId, String toId, String newTitle){
        if(type == RequestType.changeTitle){
            this.type = type;
            this.projectId = projectId;
            this.fromId = fromId;
            this.toId = ProjectRepository.getByID(projectId).getSupervisorName();
            this.newTitle = newTitle;
            this.status = RequestStatus.Pending;
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.requestId = String.valueOf(new Date().getTime()) ;
            this.requestHistory = new ArrayList<>();
        }
    }

    public Request(RequestType type, String projectId, String fromId, String newSuperId){
        if(type == RequestType.transferStudent){
            this.type = type;
            this.projectId = projectId;
            this.fromId = fromId;
            this.toId = "ASFLI";
            this.replacementSupId = newSuperId;
            this.status = RequestStatus.Pending;
            Date day = new Date();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.requestId = String.valueOf(new Date().getTime()) ;
            this.requestHistory = new ArrayList<>();
        }
    }


    public Request(RequestType type, String projectId, String studentId){
        this.fromId = studentId;
        this.toId = "ASFLI";
        this.type = type;
        this.projectId = projectId;
        this.status = RequestStatus.Pending;
        Date day = new Date();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.requestId = String.valueOf(new Date().getTime()) ;
        this.requestHistory = new ArrayList<>();
    }

    public RequestType getType() {
        return type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getToId() {
        return toId;
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

    public String getReplacementSupId() {
        return replacementSupId;
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
