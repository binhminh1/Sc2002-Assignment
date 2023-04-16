package model;

import repository.ProjectRepository;
import repository.RequestRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class Supervisor extends User implements ViewRequestHistory {

    public List<Project> projects = new ArrayList<>();

    public Supervisor(String userId, String name, String email) {
        super(userId, name, email);
    }

    /**
     * Makes use of overriding to login for supervisor
     */
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

    /**
     * Makes use of overriding to change password for supervisor
     */
    @Override
    public void ChangePassword() {
        Boolean supervisorResult = false;
        System.out.println("Please enter your new password: ");
        String newPassword = sc.next();
        this.changePassword(newPassword);
        System.out.println("Your password has been changed.");
        while (!supervisorResult) {
            supervisorResult = this.login();
        }
    }




    Scanner scanner = new Scanner(System.in);


    public void addProjects(Project project) {
        projects.add(project);
    }


    public void removeProject(Project project) {
        projects.remove(project);
    }

    public Project getProjectsById(String projectId) {
        for (Project project : ProjectRepository.getProjects()){
            if (projectId.equals(project.getProjectId())) {
                return project;
            }
        }
        return null;
    }

    /**
     * Prints projects under the supervisor
     */
    @Override
    public void viewProject() {
        for (Project project : ProjectRepository.searchProjects(null, null, super.getName())) {
            project.displayProject();
        }
    }

    public void viewAllocatedProject() {
        for (Project project : ProjectRepository.searchProjects(ProjectStatus.ALLOCATED, null, super.getName())) {
            project.displayProject();
        }
    }

    /*
     * Allows supervisor to send a request to coordinator to transfer student to another supervisor
     *
     * @param supervisorId     new supervisorID
     * @param ProjectId        projectID does not change
     * @return
     */


    /**
     * Ensures that each supervisor only have 2 projects
     *
     * @param newSupervisorName
     * @return
     */

    public boolean supervisorCapReached(String newSupervisorName) {
        int numOfProject = 0;
        for (Project project : ProjectRepository.searchProjects(null, null, newSupervisorName)) {
            if (project.getStatus().equals(ProjectStatus.ALLOCATED)) {
                numOfProject++;
            }
        }
        return numOfProject >= 2;
    }

    /**
     * Allow supervisor to view all of his requests (both incoming and outgoing)
     */
    public void viewRequestHistory() {
        List<Request> incomingRequests = RequestRepository.getRequestsByToName(this.getName());
        List<Request> outgoingRequests = RequestRepository.getRequestsByFromId(this.getUserId());

        System.out.println("Incoming Requests:");
        for (Request request : incomingRequests) {
            System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
        }

        System.out.println("Outgoing Requests:");
        for (Request request : outgoingRequests) {
            System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
        }
    }



    //OUTGOING request = transfer student

    /**
     * Approve change title request
     */
    public void changeTitle(String newTitle, String projectId) {
        Project project = ProjectRepository.getByID(projectId);
        project.setProjectTitle(newTitle);
    }

    /**
     * Allow supervisor to approve or reject student requests
     */
    public void processChangeTitleRequest() {
        List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);

        if (!pendingRequests.isEmpty()) {
            // Print all pending requests
            System.out.println("Pending requests:");
            for (Request request : pendingRequests) {
                if (request.getType() == (RequestType.changeTitle)) {
                    System.out.println("\nRequest ID: "+ request.getRequestId());
                    System.out.println("Student ID: "+ request.getFromId());
                    System.out.println("Request Type: "+ request.getType());
                    System.out.println("Request status: "+ request.getStatus());
                }
            }

            System.out.println("Enter student ID to approve/reject or 0 to exit:");
            String studentId = scanner.next();
            if (studentId.equals("0")) {
                return; // Exit loop
            }

            System.out.println("Please select an option: \n" +
                    "1. Approve \n" +
                    "2. Reject \n");
            int processChoice = scanner.nextInt();


            Request req = null;
            for (Request request1 : pendingRequests) {
                if (request1.getType() == (RequestType.changeTitle) && request1.getFromId().equals(studentId)) {
                    System.out.println("\nRequest ID: "+ request1.getRequestId());
                    System.out.println("From ID: "+ request1.getFromId());
                    System.out.println("Request Type: "+ request1.getType());
                    System.out.println("Request status: "+ request1.getStatus());
                    req = request1;
                }
            }


            if (req == null) {
                System.out.println("Invalid request ID");

                return;
            }
            // Process a request
            if (processChoice == 1) {
                req.changeStatus(RequestStatus.Approved);
                System.out.println("Request approved");
                Student student = StudentRepository.getByID(req.getFromId());
                //change title
                changeTitle(req.getNewTitle(), student.getProjectId());
            } else if (processChoice == 2) {
                req.changeStatus(RequestStatus.Rejected);
                System.out.println("Request rejected");
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    /**
     * Allow supervisor to view request history
     * @param supervisoruserid
     * @param supervisor
     */
    public void viewRequestHistory(String supervisoruserid, Supervisor supervisor) {
        System.out.println("Your requests: ");
        for (Request request : RequestRepository.getRequests()) {

            if (request.getToName().equals(supervisor.getName())) {
                System.out.println("\nIncoming requests:");
                System.out.println("Request ID: "+ request.getRequestId());
                System.out.println("Request Type: "+ request.getType());
                System.out.println("Request status: "+ request.getStatus());
                System.out.println("Time that request is sent: " + request.getTime());
            }

            if (request.getFromId().equals(supervisoruserid)) {
                System.out.println("\nOutgoing requests:");
                System.out.println("Request ID: "+ request.getRequestId());
                System.out.println("Request Type: "+ request.getType());
                System.out.println("Request status: "+ request.getStatus());
                System.out.println("Time that request is sent: " + request.getTime());
            }
        }
    }

    /**
     * Allows supervisor to create new project
     * @param supervisor
     */
    public void createProject(Supervisor supervisor) {
        System.out.println("Please enter the project name, use underscore(_) to represent whitespace");
        String projectName = sc.next();
        projectName=projectName.replace("_"," ");
        Project project = new Project(String.valueOf(ProjectRepository.numberOfProjects + 1), supervisor.getName(), projectName);
        supervisor.addProjects(project);
        ProjectRepository.addProject(project);
        System.out.println("Project created successfully");
    }

    /**
     * Allows supervisor to update project title
     * @param supervisor
     */
    public void updateProject(Supervisor supervisor) {
        supervisor.viewProject();
        System.out.println("Input the project ID to change title");
        String projectId = sc.next();
        System.out.println("Please enter the new title, use underscore(_) to represent whitespace");
        String newTitle = sc.next();
        newTitle=newTitle.replace("_"," ");
        if (supervisor.getProjectsById(projectId) != null && supervisor.getProjectsById(projectId).getSupervisorName().equals(supervisor.getName())) {
            supervisor.changeTitle(newTitle, projectId);
            System.out.println("Successfully changed");
        } else {
            System.out.println("Invalid project ID");
        }
    }

    /**
     * Allows supervisor to transfer student to another supervisor 
     */
    public void transferStudentRequest(Supervisor supervisor) {
        while (true) {
            supervisor.viewAllocatedProject();
            System.out.println("");
            System.out.println("Please enter the project ID: ");
            String projectId = sc.next();
            Project project = ProjectRepository.getByID(projectId);
            if (project == null || !project.getSupervisorName().equals(super.getName())) {
                System.out.println("Invalid project ID");
                continue;
            }
            if (project.getStatus() != ProjectStatus.ALLOCATED) {
                System.out.println("The project has not been allocated to a student");
                continue;
            }
            System.out.println("Please enter the replacement supervisor ID: ");

            String supervisorIdToTransfer = sc.next();
            System.out.println(supervisorIdToTransfer);
            Supervisor trasferSuper = SupervisorRepository.getByID(supervisorIdToTransfer);
            if (trasferSuper == null) {
                System.out.println("Invalid supervisor ID. Please try again.");
                continue;
            }
            if (trasferSuper.supervisorCapReached(supervisorIdToTransfer)) {
                System.out.println("Replacement supervisor is already supervising 2 projects. Please try again.");
                continue;
            }

            Request request = new Request(RequestType.transferStudent, projectId, super.getUserId(), trasferSuper.getName());
            RequestRepository.addRequest(request);
            break;
        }
        System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
    }
}

