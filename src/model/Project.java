package model;

import repository.ProjectRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;
import service.ProjectService;
import service.StudentService;
import service.SupervisorService;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Project {
    private String projectId;
    private String supervisorId;
    private String projectTitle;
    private String studentId;
    private ProjectStatus status; 


    public Project(String projectTitle, String supervisorId) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.projectId = df.format(day);
        this.projectTitle = projectTitle;
        this.supervisorId =  supervisorId;
        this.studentId = "NULL";
        this.status = ProjectStatus.AVAILABLE;
    }
    public void displaySupervisorInformation() {
        Supervisor supervisor = SupervisorRepository.getByID(supervisorId);//(need to change)
        System.out.println("Supervisor Name: " + supervisor.getName());
        System.out.println("Supervisor Email Address: " + supervisor.getEmail());
    }
    public void displayStudentInformation(){
        Student student = StudentRepository.getByID(studentId);//(need to change)
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Email Address: " + student.getEmail());
    }

    public void displayProjectID() {
        System.out.println("Project ID: " + projectId);
    }
    public void displayProjectInformation() {
        System.out.println("Project Title: " + projectTitle);
        System.out.println("Project Status: " + status);
    }

    public void displayProject() {
        if (status == ProjectStatus.AVAILABLE) {
            System.out.println("Project is available for allocation.");
            displayProjectID();
            displaySupervisorInformation();
            displayProjectInformation();
        } else if (status == ProjectStatus.ALLOCATED) {
            System.out.println("Project is allocated to a student.");
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
    public boolean reserve(String studentID){
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

    public boolean Transfer(String studentID,String supervisorId,String projectId){
        if(this.status != ProjectStatus.ALLOCATED){
            return false;
        }
        Project project = ProjectService.getByID(projectId);
        if(project.getSupervisorId() != supervisorId){
            return false;
        }
        project.studentId = studentID;
        this.studentId = "NULL";
        this.status = ProjectStatus.AVAILABLE;
        return true;
    }
    public String getProjectId(){
        return projectId;
    }
    public String getStudentId(){
        return studentId;
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

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
