package model;

import repository.ProjectRepository;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static model.StudentStatus.PENDING;
import static model.StudentStatus.UNREGISTERED;

/**
 * Makes use of inheritance and extends from User Class 
 */
public class Student extends User implements ViewRequestHistory {
    /**
     * initialize private variables to be used in class
     */
    private StudentStatus status;
    private String toId;
    private String projectId;

    private String Superid;
    Scanner sc = new Scanner(System.in);

    public Student(String userId, String name, String email) {
        super(userId, name, email);
        this.projectId = "NULL";
        this.Superid = "NULL";
        this.status = StudentStatus.UNREGISTERED;
    }

    @Override
    public Boolean login() {
        System.out.println("Enter your password: ");
        String password = sc.next();

        if (Objects.equals(password, this.getPassword())) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Wrong user ID or password. Please try again.");
            return false;
        }
    }





    public void ChangePassword() {

        Boolean studentResult = false;
        System.out.println("Please enter your new password: ");
        String newPassword = sc.next();
        this.changePassword(newPassword);
        System.out.println("Your password has been changed.");
        while (!studentResult) {
            studentResult = this.login();
        }
    }


    public void changeStatus(StudentStatus status) {
        this.status = status;
    }

    public void changeProjectId(String projectId) {
        this.projectId = projectId;
        this.Superid = ProjectRepository.getByID(projectId).getSupervisorName();
    }

    public StudentStatus getStatus() {
        return status;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getSuperid() {
        return Superid;
    }

    public void viewAvailableProjects(Student student){
        if(student.getStatus().equals(StudentStatus.DEREGISTERED)){
            System.out.println("You are not allowed to make selection again as you deregistered your FYP");
        }
        System.out.println("Available projects: ");
        for (Project project : ProjectRepository.getAvailableProject()) {
            project.displayProject();
        }
    }


    public Request sendSelectProjectRequest(Student student) {
        if (student.getStatus() == UNREGISTERED) {
            System.out.println("Please select a project to register: ");

            while (true) {
                //print all available projects
                for (Project project : ProjectRepository.getAvailableProject()) {
                    System.out.println(project.getProjectId() + " " + project.getProjectTitle() + " " + project.getSupervisorName());

                }
                //student input the project id
                String projectId = sc.next();
                Project project = ProjectRepository.getByID(projectId);
                if (project != null && (project.getStatus().equals(ProjectStatus.AVAILABLE))) {

                    Request request = new Request(RequestType.assignProject, projectId, super.getUserId());
                    RequestRepository.addRequest(request);
                    ProjectRepository.getByID(projectId).setStatus(ProjectStatus.RESERVED);
                    ProjectRepository.getByID(projectId).setStudentId(super.getUserId());
                    System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
                    student.changeStatus(PENDING);
                    return request;
                }
                else{
                    System.out.println("Your choice is invalid. Please select a valid project.");
                }
            }
        } else {
            System.out.println("You are unable to register or deregister a project at this moment.");
            return null;
        }
    }




    public Request sendDeregisterProjectRequest(Student student) {
        if (student.getStatus() == StudentStatus.REGISTERED) {
            Request request = new Request(RequestType.deregister, student.getProjectId(), super.getUserId());
            RequestRepository.addRequest(request);
            System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
            return request;
        } else {
            System.out.println("You are unable to deregister a project at this moment.");
            return null;
        }
    }

    public List<String> viewOutgoingRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();

        for (Request request : RequestRepository.getRequestsByFromId(this.getUserId())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nProject ID: ").append(request.getProjectId())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToName())
                    .append("\nReplacement supervisor: ").append(request.getReplacementSupName())
                    .append("\nStatus: ").append(request.getStatus());

            if (!request.getRequestHistory().isEmpty()) {
                sb.append("\nHistory:");
                for (RequestHistory history : request.getRequestHistory()) {
                    sb.append("\n- ").append(history.getStatus())
                            .append(" on ").append(history.getUpdatedDate());
                }
            }
            requestHistory.add(sb.toString());
        }
        return requestHistory;
    }

    @Override
    public void viewRequestHistory() {
    }

    public void viewRequestHistory(String studentID){
        for (Request request : RequestRepository.getRequestsByFromId(studentID)){
            System.out.println("Request ID: "+ request.getRequestId());
            System.out.println("Student ID: "+ request.getFromId());
            System.out.println("Request Type: "+ request.getType() );
            System.out.println("Request Status: "+request.getStatus());
            System.out.println(" ");
        }
    }

    @Override
    public void viewProject() {
    }

    public void viewProject(Student student){
        if (student.getStatus() == StudentStatus.REGISTERED) {
            for (Project project : ProjectRepository.getProjectsByStatus(ProjectStatus.ALLOCATED)) {
                if (student.getUserId().equals(project.getStudentId())) {
                    project.displayProjectID();
                    project.displaySupervisorInformation();
                    project.displayProjectInformation();

                }
            }
        } else {
            System.out.println("You have not registered a project yet.");
        }
    }
    public Request sendChangeTitleRequest(Student student){
        if (student.getStatus() == StudentStatus.REGISTERED) {
            System.out.println("Please enter the new title of your project: ");
            String newTitle = sc.next();

            Request request = new Request(RequestType.changeTitle, student.getProjectId(), super.getUserId(), toId, newTitle);
            RequestRepository.addRequest(request);
            System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
            return request;
        }
        else{
            System.out.println("You have not registered a project yet.");
            return null;
        }
    }

}

