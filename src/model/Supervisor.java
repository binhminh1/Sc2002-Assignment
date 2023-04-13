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
        for (Project project : projects) {
            if (Objects.equals(project.getProjectId(), projectId)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Prints projects under the supervisor
     */
    public void viewProjects() {
        for (Project project : ProjectRepository.searchProjects(null, null, super.getName())) {
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
    public boolean sendTransferStudentRequest(String newsupervisorName, String ProjectId) {
        Project project = ProjectRepository.getByID(ProjectId);
        if (project == null || !project.getSupervisorName().equals(super.getName())) {
            System.out.println("Invalid project ID");
            return false;
        }
        if (project.getStatus() == ProjectStatus.ALLOCATED) {
            Request request = new Request(RequestType.transferStudent, ProjectId, super.getUserId(), newsupervisorName);
            RequestRepository.addRequest(request);
            return true;
        } else {
            System.out.println("The project has not been allocated to a student");
            return false;
        }
    }

    /**
     * Ensures that each supervisor only have 2 projects
     *
     * @param newSupervisorId
     * @return
     */
    public boolean supervisorCapReached(String newSupervisorId) {
        int numOfProject = 0;
        for (Project project : projects) {
            if (Objects.equals(project.getSupervisorName(), newSupervisorId)) {
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
                    System.out.println(request.getRequestId() + " from: " + request.getFromId() + " " + request.getType() + " " + request.getStatus());
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
                    System.out.println(request1.getRequestId() + " from: " + request1.getFromId() + request1.getType() + " " + request1.getStatus());
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

    public void viewRequestHistory(String supervisoruserid, Supervisor supervisor) {
        for (Request request : RequestRepository.getRequests()) {

            if (request.getToName().equals(supervisor.getName())) {
                System.out.println("Incoming requests:");
                System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
            }

            if (request.getFromId().equals(supervisoruserid)) {
                System.out.println("Outgoing requests:");
                System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
            }
        }
    }
}
