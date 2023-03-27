package model;

public class Project {
    private String projectId;
    private String projectTitle;
    private String supervisorId;
    private ProjectStatus status;

    private String studentId;

    public Project(String projectID, String projectTitle, String supervisorId,String studentId) {
        this.projectId = projectID;
        this.projectTitle = projectTitle;
        this.supervisorId = supervisorId;
        this.studentId = "NULL";
        this.status = ProjectStatus.AVAILABLE;
    }

    ·
    private void displaySupervisorInformation() {
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
    public boolean Select(String studentID){
        if (status != ProjectStatus.AVAILABLE) {
            return false;
        }
        this.studentId = studentID;
        this.status = ProjectStatus.RESERVED;
        return true;
    }

    public boolean Allocate(String studentID){
        if (status != ProjectStatus.RESERVED) {
            return false;
        }
        this.studentId = studentID;
        this.status = ProjectStatus.ALLOCATED;
        return true;
    }
    public boolean Recycle(){
        if (this.status != ProjectStatus.ALLOCATED && this.status != ProjectStatus.RESERVED) {
            return false;
        }
        this.status = ProjectStatus.AVAILABLE;
        this.studentId = "";
        return true;

    }

    public String getProjectId(){
        return projectId;
    }
    public ProjectStatus getStatus(){
        return status;
    }


    public String getProjectTitle(){
        return projectTitle;
    }
    public String getSupervisorId() {return supervisorId;}

    public void setProjectId(String projectId){
        this.projectId = projectId;
    }



    public void setProjectTitle(String projectTitle){
        this.projectTitle = projectTitle;
    }

    public void setStatus(ProjectStatus status){
        this.status = status;
    }


}
