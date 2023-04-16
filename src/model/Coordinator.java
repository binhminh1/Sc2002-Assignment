package model;
 
import java.util.Objects;
import java.util.Scanner;

import repository.ProjectRepository;
import repository.RequestRepository;
import repository.SupervisorRepository;
import repository.StudentRepository; 

import java.util.ArrayList;
import java.util.List;
/**
 * Methods that coordinator can do
 */
public class Coordinator extends User implements ViewRequestHistory{
    private String userId;
    private String password = "password";
    private String email;
    private String name;

    public Coordinator(String userId, String name, String email) {
        super(userId, name, email);
        this.userId = "ASFLI";
        this.name = name;
        this.email = email;
        this.password = "password";
    }

    /**
     * Prompts the user to enter a new password, changes the password of the Coordinator object calling the method, and
     * logs in the user with the new password. The method loops until the login attempt is successful.
     *
     * @return void
     */
    @Override
    public void ChangePassword() {
        Boolean coorResult = false;
        System.out.println("Please enter your new password: ");
        String newPassword = sc.next();
        this.changePassword(newPassword);
        System.out.println("Your password has been changed.");
        while (!coorResult) {
            coorResult = this.login();
        }
    }

    @Override
    public void viewProject() {
    }
    /**
     * Prompts the user to enter their password and compares it with the stored password of the Coordinator object calling
     * the method. If the passwords match, the method returns true and prints a success message. Otherwise, the method
     * returns false and prints an error message.
     *
     * @return a Boolean indicating whether the login attempt was successful
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
     * Displays all available projects in the ProjectRepository by calling the displayProject() method for each project.
     *
     * @return void
     */
    public void viewProjects(){
        for(Project project : ProjectRepository.getProjects()){
            project.displayProject();
        }
    }
    /**
     * Changes the supervisor of the project with the specified ID to the supervisor with the specified name, if the
     * supervisor is not already supervising the maximum number of projects. If the supervisor is already supervising the
     * maximum number of projects, prints an error message. If the project with the specified ID is not found, prints an
     * error message.
     *
     * @param projectId the ID of the project to update
     * @param newSupervisorName the name of the new supervisor to assign to the project
     * @return void
     */

    public void changeProjectSupervisor(String projectId, String newSupervisorName) {
        Project projectToUpdate = ProjectRepository.getByID(projectId);
        if(SupervisorRepository.getByName(newSupervisorName).supervisorCapReached(newSupervisorName))
        {
            System.out.println("Supervisor has reached maximum number of supervising projects");
        }
        
        else 
        {
            if (projectToUpdate != null) {
            projectToUpdate.setSupervisorName(newSupervisorName);
            System.out.println("Project supervisor updated.");
            } 
        else {
            System.out.println("Project not found.");
            }
        }
    }
    /**
     * Assigns the specified project to the specified student by updating the project's student ID and status fields and
     * the student's project ID and status fields. If the project with the specified ID is not found, prints an error
     * message. If the project is already allocated to a student, prints an error message.
     *
     * @param projectId the ID of the project to allocate
     * @param studentId the ID of the student to allocate the project to
     * @return void
     */
    public void allocateProject(String projectId, String studentId) {
        Project project = ProjectRepository.getByID(projectId);
    
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
    
        // Check if the project is already allocated to a student
        if (project.getStatus().equals(ProjectStatus.ALLOCATED)) {
            System.out.println("Project is already allocated to a student.");
            return;
        }
    
        // Update the project's student ID and status fields

        Student student=  StudentRepository.getByID(studentId);
        project.setStudentId(studentId);
        student.changeProjectId(projectId);
        student.changeStatus(StudentStatus.REGISTERED);
        project.setStatus(ProjectStatus.ALLOCATED);
        System.out.println("Project allocated to student.");
    }
    /**
     * Removes the allocation of the project with the specified ID from its student, sets the project's student ID and
     * status fields to null and "AVAILABLE", respectively, and sets the student's project ID and status fields to null and
     * "DEREGISTERED", respectively. If the project with the specified ID is not found, prints an error message. If the
     * student with the ID of the previously allocated student is not found, prints an error message.
     *
     * @param projectId the ID of the project to deregister
     * @return void
     */

    public void deregisterStudentFromFYP(String projectId) {
        Project projectToUpdate = ProjectRepository.getByID(projectId);
    
        if (projectToUpdate != null) {
            String studentId = projectToUpdate.getStudentId();
            projectToUpdate.setStudentId(null);
            projectToUpdate.setStatus(ProjectStatus.AVAILABLE);
            System.out.println("Project deregistered from student.");
            
            // Update student status
            Student studentToUpdate = StudentRepository.getByID(studentId);
            if (studentToUpdate != null) {
                studentToUpdate.changeStatus(StudentStatus.DEREGISTERED);
                System.out.println("Student deregistered from FYP.");
            } else {
                System.out.println("Student not found.");
            }
        } else {
            System.out.println("Project not found.");
        }
    }
    /**
     * Changes the title of the project with the specified ID to the specified new title. If the project with the specified
     * ID is not found, prints an error message.
     *
     * @param projectId the ID of the project to update
     * @param newTitle the new title to assign to the project
     * @return void
     */

    public void changeTitle(String projectId,String newTitle){
        Project project = ProjectRepository.getByID(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        project.setProjectTitle(newTitle);
    }
    
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Displays a report of all projects matching the specified filters, including project status, supervisor name, and/or
     * student ID. Asks the coordinator to select filter options and values via the console, and searches for projects
     * matching the selected filters in the ProjectRepository. If no projects match the selected filters, prints a message
     * indicating that no projects were found. If an invalid input is provided at any point during the process, prints an
     * error message and exits the method.
     *
     * @return void
     */
    public void displayReportByFilters() {
        // Ask coordinator for filter options
        System.out.println("Please select filter options:");
        System.out.println("1. Project status");
        System.out.println("2. Supervisor name");
        System.out.println("3. Student ID");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            // Get selected filter values
            ProjectStatus statusFilter = null;
            String supervisorNameFilter = null;
            String studentIdFilter = null;
            switch (choice) {
                case 1:
                    System.out.println("Enter project status filter (UNAVAILABLE, AVAILABLE, RESERVED, or ALLOCATED):");
                    statusFilter = ProjectStatus.valueOf(scanner.next().toUpperCase());
                    break;
                case 2:
                    while(true) {
                        System.out.println("Enter supervisor name filter:");
                        supervisorNameFilter = scanner.nextLine();
                        if(SupervisorRepository.getByName(supervisorNameFilter) != null){
                            break;
                        }

                        System.out.println("Invalid supervisor name");
                    }
                    break;
                case 3:
                    while(true){
                    System.out.println("Enter student ID filter:");
                    studentIdFilter = scanner.next();
                    if(StudentRepository.getByID(studentIdFilter) != null){
                        break;
                    }
                    System.out.println("Invalid student ID");
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }

            // Search for projects matching the selected filters
            List<Project> matchingProjects = ProjectRepository.searchProjects(statusFilter, studentIdFilter, supervisorNameFilter);
            if(matchingProjects.isEmpty()){
                System.out.println("No projects found");
                return;
            }
            // Print the details of the matching projects
            for (Project project : matchingProjects) {
               project.displayProject();
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    /**

     Displays all pending requests and allows coordinator to approve or reject them based on their type.
     Types of requests include transferStudent, assignProject, deregister, and changeTitle.
     For each request, the details of the request are printed, and the coordinator can select to approve or reject it.
     Depending on the request type, different actions are taken upon approval or rejection, such as changing the project supervisor,
     allocating a project to a student, deregistering a student from a project, or changing the title of a project.
     The method loops through all pending requests until there are none left, or until the coordinator selects to exit.
     */
    public void processPendingRequests() {
        List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);

        while (!pendingRequests.isEmpty()) {
            // Print all pending requests
            for (Request request : pendingRequests) {
                if(request.getType() == RequestType.transferStudent){
                    System.out.println("\nRequest ID: " + request.getRequestId());
                    System.out.println("Request Time: " + request.getTime());
                    System.out.println("Student Name: "+(SupervisorRepository.getByID(request.getFromId())).getName());
                    System.out.println("Request Type: Transfer student to another supervisor");
                    System.out.println("Project ID: " + request.getProjectId());
                    System.out.println("Original supervisor ID: " + request.getFromId());
                    System.out.println("New supervisor ID: " + request.getReplacementSupName());
                    System.out.println("Request status: " + request.getStatus() + "\n");
                }
                else if(request.getType() == RequestType.assignProject){
                    System.out.println("\nRequest ID: " + request.getRequestId());
                    System.out.println("Request Time: " + request.getTime());
                    System.out.println("Student ID: " + request.getFromId());
                    System.out.println("Student Name: " +(StudentRepository.getByID(request.getFromId())).getName());
                    System.out.println("Request Type: Assign project to student");
                    System.out.println("Project ID: "+ request.getProjectId());
                    System.out.println("Request status: " + request.getStatus() + "\n");
                }
                else if (request.getType() == RequestType.changeTitle){
                    System.out.println("\nRequest ID: " + request.getRequestId());
                    System.out.println("Request Time: " + request.getTime());
                    System.out.println("Student ID: " + request.getFromId());
                    System.out.println("Student Name: "+(StudentRepository.getByID(request.getFromId())).getName());
                    System.out.println("Request Type: Student request to change title of project");
                    System.out.println("Project ID: "+ request.getProjectId());
                    System.out.println("New project title: "+request.getNewTitle());
                    System.out.println("Request status: " + request.getStatus() + "\n");
                }
                else{ //deregister
                    System.out.println("\nRequest ID: " + request.getRequestId());
                    System.out.println("Request Time: " + request.getTime());
                    System.out.println("Student ID: " + request.getFromId());
                    System.out.println("Student Name: "+(StudentRepository.getByID(request.getFromId())).getName());
                    System.out.println("Request Type: Student request to deregister from project");
                    System.out.println("Project ID: "+ request.getProjectId());
                    System.out.println("Request status: " + request.getStatus() + "\n");
                }
            }

            // Process a request, ask for requestID
            System.out.println("\nEnter request ID to approve/reject or 0 to exit:");
            String requestId = scanner.next();
//            scanner.next(); // Consume the newline character

            if (requestId .equals("0") ) {
                break; // Exit loop
            }

            Request request = RequestRepository.getByID(String.valueOf(requestId));
            if (request != null) {
                System.out.println("\nPlease select an option: \n" +
                        "1. Approve \n" +
                        "2. Reject \n");
                int processChoice = scanner.nextInt();
//                scanner.next(); // Consume the newline character

                switch (request.getType()) {
                    case transferStudent:
                        if (processChoice == 1) {
                            request.changeStatus(RequestStatus.Approved);
                            changeProjectSupervisor(request.getProjectId(), request.getReplacementSupName());
                            System.out.println("The request has been approved.");
                        } else {
                            System.out.println("The request has been rejected.");
                            request.changeStatus(RequestStatus.Rejected);
                        }
                        break;
                    case assignProject:
                        if (processChoice == 1) {
                            Project projectID = ProjectRepository.getByID(request.getProjectId());
                            String supervisorName = projectID.getSupervisorName();
                            Supervisor supervisor = SupervisorRepository.getByName(supervisorName);
                            if (supervisor.supervisorCapReached(supervisorName) == false) {
                                request.changeStatus(RequestStatus.Approved);
                                allocateProject(request.getProjectId(), request.getFromId());
                                System.out.println("The request has been approved.");
                            }
                            else{
                                System.out.println("The supervisor already got 2 students.");
                                System.out.println("Your request has been rejected.");
                                request.changeStatus(RequestStatus.Rejected);
                                StudentRepository.getByID(request.getFromId()).changeStatus(StudentStatus.UNREGISTERED);
                                ProjectRepository.getByID(request.getProjectId()).setStatus(ProjectStatus.UNAVAILABLE);
                                for (Project project : ProjectRepository.searchProjects(null, null, supervisorName)) {
                                    if (project.getStatus().equals(ProjectStatus.AVAILABLE)) {
                                        project.setStatus(ProjectStatus.UNAVAILABLE);
                                    }
                                }
                            }
                        } else {
                            System.out.println("The request has been rejected.");
                            request.changeStatus(RequestStatus.Rejected);
                            StudentRepository.getByID(request.getFromId()).changeStatus(StudentStatus.UNREGISTERED);
                            ProjectRepository.getByID(request.getProjectId()).setStatus(ProjectStatus.AVAILABLE);
                        }
                        break;
                    case deregister:
                        if (processChoice == 1) {
                            request.changeStatus(RequestStatus.Approved);

                            deregisterStudentFromFYP(request.getProjectId());
                            System.out.println("The request has been approved.");
                        } else {
                            System.out.println("The request has been rejected.");
                            request.changeStatus(RequestStatus.Rejected);
                            StudentRepository.getByID(request.getFromId()).changeStatus(StudentStatus.REGISTERED);
                        }
                        break;
                    case changeTitle:
                        if (processChoice == 1) {
                            request.changeStatus(RequestStatus.Approved);
                            changeTitle(request.getProjectId(), request.getNewTitle());
                            System.out.println("The request has been approved.");
                        } else {
                            System.out.println("The request has been rejected.");
                            request.changeStatus(RequestStatus.Rejected);
                        }
                        break;
                }
            } else {
                System.out.println("Request not found.");
            }

            // Update pending requests list
            pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);
        }

        if (pendingRequests.isEmpty()) {
            System.out.println("\nAll pending requests processed.");
        }
    }

    //View all History (overriding)
    @Override
    public void viewRequestHistory() {
    }

    /**

     Displays the history of all requests made in the system, including their details such as request ID, request time,
     request type, from ID, to name, and request status.
     */

    public void viewRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();

        for (Request request : RequestRepository.getRequests()) {

            System.out.println("Request ID: "+ request.getRequestId());
            System.out.println("Request Time: " + request.getTime());
            System.out.println("Request Type: "+ request.getType());
            System.out.println("From ID: "+request.getFromId());
            System.out.println("To Name: "+ request.getToName());
            System.out.println("Request status: " + request.getStatus());
            System.out.println(" ");
        }
    }
}