public class Project {
    private String ProjectId;
    private String SupervisorName;
    private String SupervisorEmail;
    private String ProjectTitle;

    private String StudentName;

    private String StudentEmailAddress;


    private ProjectStatus status;
    public String getProjectId(){
        return ProjectId;
    }
    public ProjectStatus getStatus(){
        return status;
    }
    public String getSupervisorName(){
        return SupervisorName;
    }

    public String getSupervisorEmail(){
        return SupervisorEmail;
    }

    public String getProjectTitle(){
        return ProjectTitle;
    }

    public void setProjectId(String projectId){
        ProjectId = projectId;
    }

    public void setSupervisorName(String supervisorName){
        SupervisorName = supervisorName;
    }

    public void setSupervisorEmail(String supervisorEmail){
        SupervisorEmail = supervisorEmail;
    }

    public void setProjectTitle(String projectTitle){
        ProjectTitle = projectTitle;
    }

    public void setStatus(ProjectStatus status){
        this.status = status;
    }


}
