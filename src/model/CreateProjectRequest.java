package model;

public class CreateProjectRequest {
    private String projectTitle;
    private String supervisorName;
    private String supervisorEmail;

    public CreateProjectRequest(String projectTitle,String supervisorName,String supervisorEmail){
        this.projectTitle = projectTitle;
        this.supervisorName = supervisorName;
        this.supervisorEmail = supervisorEmail;
    }

}
