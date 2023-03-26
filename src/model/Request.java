package model;

public class Request {
    private RequestType type;

    private String ProjectId;
    private String fromId;
    private String replacementSupId;
    private String newTitle;

    private String requestId;

    private RequestStatus status;

    //for change title or transferStudent
    public Request(RequestType type, String projectID, String fromID, String comment){
        if(type == RequestType.changeTitle){
            this.type = type;
            this.ProjectId = projectID;
            this.fromId = fromID;
            this.newTitle = comment;
            this.status = RequestStatus.Pending;
        }
        if(type == RequestType.transferStudent){
            this.type = type;
            this.ProjectId = projectID;
            this.fromId = fromID;
            this.replacementSupId = comment;
            this.status = RequestStatus.Pending;
        }

    }

    //for assign project
    public Request(RequestType type, String ProjectID, String fromId){
        this.type = type;
        this.ProjectId = ProjectID;
        this.fromId = fromId;
        this.status = RequestStatus.Pending;
    }

    //for deregister
    public Request(RequestType type, String ProjectID){
        this.type = type;
        this.ProjectId = ProjectID;
        this.status = RequestStatus.Pending;
    }
}
