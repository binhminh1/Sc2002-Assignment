package model;

public class ChangeTitleRequest {
    private String newTitle;

    public ChangeTitleRequest(String projectId,String newTitle){

        this.newTitle = newTitle;
    }
}
