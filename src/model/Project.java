package model;

import repository.StudentRepository;
import repository.SupervisorRepository;
import service.ProjectService;

import java.util.Date;

public class Project {
    /** 
     * Declaration of variables to be used within the Project Class
     */
    private String projectId;
    private String supervisorName;
    private String projectTitle;

    private String studentId;
    private ProjectStatus status; 


    public Project(String o, String supervisorId, String projectTitle) {


        this.projectId=o;
        this.projectTitle = projectTitle;
        this.supervisorName =  supervisorId;
//        this.studentId = o;
        this.status = ProjectStatus.AVAILABLE;
    }

    /**
     * Print the name and email of the supervisor.
     */
    /*public void displaySupervisorInformation() {
        Supervisor supervisor = SupervisorRepository.getByName(supervisorName);//(need to change)
        System.out.println("Supervisor Name: " + supervisor.getName());
        System.out.println("Supervisor Email Address: " + supervisor.getEmail());
    }*/

    /**
     * Print the name and email of the student.
     */
    public void displayStudentInformation(){
        Student student = StudentRepository.getByID(studentId);//(need to change)
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Email Address: " + student.getEmail());
    }

    /**
     * Print the ID of the project.
     */
    public void displayProjectID() {
        System.out.println("Project ID: " + projectId);
    }

    /**
     * Print the title and status of the project.
     */
    public void displayProjectInformation() {
        System.out.println("Project Title: " + projectTitle);
        System.out.println("Project Status: " + status);
    }

    /**
     * Print all the projects and display the projectID, information of the project and supervisor information.<!-- -->
     * For projects that are allocated, the information of the student that is allocated to the project is also printed.
     */
    public void displayProject() {
        if (status == ProjectStatus.AVAILABLE) {

            displayProjectID();
            //displaySupervisorInformation();
            displayProjectInformation();

        } else if (status == ProjectStatus.ALLOCATED) {

            displayProjectID();
            //displaySupervisorInformation();
            displayStudentInformation();
            displayProjectInformation();

        } else {
            throw new IllegalStateException("Project status is not AVAILABLE or ALLOCATED.");
        }
    }

    /**
     * Assign a student to the project 
     * (change project status to reserved so that no otehr student can choose it until request is approved/rejected)
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

    /**
     * Allocate student to the project if project is not reserved
     * 
     * @param studentID the studentID of student that is to be allocated to the project
     * @return true if student is allocated to the project
     */
    public boolean Allocate(String studentID){
        if (status != ProjectStatus.RESERVED) {
            return false;
        }
        this.studentId = studentID;
        this.status = ProjectStatus.ALLOCATED;
        return true;
    }

    /**
     * Changes project status to available after student drops project
     * 
     * @return true when request from student to deregister from project
     */
    public boolean Recycle(){
        if (this.status != ProjectStatus.ALLOCATED && this.status != ProjectStatus.RESERVED) {
            return false;
        }
        this.status = ProjectStatus.AVAILABLE;
        this.studentId = "";
        return true;
    }

    /**
     * 
     * @param studentID 
     * @param supervisorId 
     * @param projectId 
     * @return true when 
     */
    public boolean Transfer(String studentID,String supervisorId,String projectId){
        if(this.status != ProjectStatus.ALLOCATED){
            return false;
        }
        Project project = ProjectService.getByID(projectId);
        if(project.getSupervisorName() != supervisorId){
            return false;
        }
        project.studentId = studentID;
        this.studentId = "NULL";
        this.status = ProjectStatus.AVAILABLE;
        return true;
    }

    /**
     * 
     * @return get projectID
     */
    public String getProjectId(){
        return projectId;
    }

    /**
     * 
     * @return get studentID
     */
    public String getStudentId(){
        return studentId;
    }

    /**
     * 
     * @return get project status
     */
    public ProjectStatus getStatus(){
        return status;
    }

    /**
     * 
     * @return get title of project
     */
    public String getProjectTitle(){
        return projectTitle;
    }

    /**
     * 
     * @return get supervisorID
     */
    public String getSupervisorName() {
        return supervisorName;
    }

    /**
     * set projectID
     * @param projectId
     */
    public void setProjectId(String projectId){
        this.projectId = projectId;
    }

    /**
     * set title of project
     */
    public void setProjectTitle(String projectTitle){
        this.projectTitle = projectTitle;
    }

    /**
     * set the project status
     */
    public void setStatus(ProjectStatus status){
        this.status = status;
    }

    /**
     * set the supervisorID
     * @param supervisorName
     */
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    /**
     * set the studentID
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
