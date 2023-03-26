package model;

public class Project {
    private String projectId;
    private String supervisorId;
    private String supervisorName;
    private String supervisorEmail;
    private String projectTitle;
    private String studentName;
    private String studentEmailAddress;
    private String studentId;
    private ProjectStatus status;

    public Project(String projectID, String projectTitle, String supervisorEmail, String supervisorName) {
        this.projectId = projectID;
        this.projectTitle = projectTitle;
        this.supervisorEmail = getSupervisorEmail();
        this.supervisorName = getSupervisorName();
        this.studentId = "NULL";
        this.status = ProjectStatus.AVAILABLE;
    }
    /*private void displaySupervisorInformation() {
        Supervisor supervisor = FacultyRepository.getInstance().getByID(supervisorID);//(need to change)
        System.out.println("Supervisor Name: " + supervisor.getName());
        System.out.println("Supervisor Email Address: " + supervisor.getEmail());
    }
    private void displayStudentInformation() {
        Student student = StudentRepository.getInstance().getByID(studentID);//(need to change)
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Email Address: " + student.getEmail());
    }

    private void displayProjectID() {
        System.out.println("Project ID: " + projectId);
    }

    private void displayProjectInformation() {
        System.out.println("Project Title: " + projectTitle);
        System.out.println("Project Status: " + status);
    }

    public void displayProject() {
        if (status == ProjectStatus.AVAILABLE) {
            displayProjectID();
            displaySupervisorInformation();
            displayProjectInformation();
        } else if (status == ProjectStatus.ALLOCATED) {
            displayProjectID();
            displaySupervisorInformation();
            displayStudentInformation();
            displayProjectInformation();
        } else {
            throw new IllegalStateException("Project status is not AVAILABLE or ALLOCATED.");
        }
    }

    /**
     * Assign a student to the project
     *
     * @param studentID the student to be assigned
     * @throws IllegalStateException if the project is not available for allocation
     */
    public void assignStudent(String studentID) throws IllegalStateException {
        if (status != ProjectStatus.AVAILABLE) {
            throw new IllegalStateException("Fail to assign student to project. Project is not available for allocation.");
        }
        this.studentId = studentID;
        this.status = ProjectStatus.ALLOCATED;
    }

    public String getProjectId(){
        return projectId;
    }
    public ProjectStatus getStatus(){
        return status;
    }
    public String getSupervisorName(){
        return supervisorName;
    }

    public String getSupervisorEmail(){
        return supervisorEmail;
    }

    public String getProjectTitle(){
        return projectTitle;
    }
    public String getSupervisorId() {return supervisorId;}

    public void setProjectId(String projectId){
        this.projectId = projectId;
    }

    public void setSupervisorName(String supervisorName){
        this.supervisorName = supervisorName;
    }

    public void setSupervisorEmail(String supervisorEmail){
        this.supervisorEmail = supervisorEmail;
    }

    public void setProjectTitle(String projectTitle){
        this.projectTitle = projectTitle;
    }

    public void setStatus(ProjectStatus status){
        this.status = status;
    }


}
