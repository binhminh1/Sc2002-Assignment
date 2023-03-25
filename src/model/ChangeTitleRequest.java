package model;

public class ChangeTitleRequest {
    private String projectId;
    private String projectTitle;

    public ChangeTitleRequest(String projectId,String projectTitle){
        this.projectId = projectId;
        this.projectTitle = projectTitle;
    }
}
