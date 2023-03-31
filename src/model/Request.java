package model;

import java.util.Date;
import java.text.SimpleDateFormat;
public class Request {
    private RequestType type;//？
    private String ProjectId;
    private String fromId;
    private String toId;
    private String replacementSupId;
    private String newTitle;
    public String requestId;
    private RequestStatus status;
    public String getRequestId(){
        return this.requestId;
    }
    public void setRequestId(String requestId) {this.requestId = requestId;}

    //for change title or transferStudent
    public Request(RequestType type, String projectID, String fromID, String toId, String newTitle){
        if(type == RequestType.changeTitle){
            this.type = type;
            this.ProjectId = projectID;
            this.fromId = fromID;
            this.toId = toId;
            this.newTitle = newTitle;
            this.status = RequestStatus.Pending;
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.requestId = df.format(day);

        }


    }
    //for transferStudent
    public Request(RequestType type, String projectID, String fromID,String newSuperId){

        if(type == RequestType.transferStudent){
            this.type = type;
            this.ProjectId = projectID;
            this.fromId = fromID;
            this.toId = "NULL";
            this.replacementSupId = newSuperId;
            this.status = RequestStatus.Pending;
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.requestId = df.format(day);
        }

    }

    //for assign project
    public Request(RequestType type, String ProjectID, String fromId){
        this.type = type;
        this.ProjectId = ProjectID;
        this.fromId = fromId;
        this.status = RequestStatus.Pending;
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.requestId = df.format(day);
    }

    //for deregister
    public Request(RequestType type, String ProjectID){
        this.type = type;
        this.ProjectId = ProjectID;
        this.status = RequestStatus.Pending;
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.requestId = df.format(day);
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
}
