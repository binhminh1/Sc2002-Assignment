package model;
 
import java.util.Scanner;

import repository.ProjectRepository;
import repository.RequestRepository;
import repository.SupervisorRepository;
import repository.StudentRepository; 

import java.util.ArrayList;
import java.util.List;

public class Coordinator extends User{
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

    
    public void viewProjects(){
        for(Project project : ProjectRepository.getProjects()){
            project.displayProject();
        }
    }
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


    public void allocateProject(String projectId, String studentId) {
        Project project = ProjectRepository.getByID(projectId);
    
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
    
        // Check if the project is already allocated to a student
        if (project.getStudentId() != null) {
            System.out.println("Project is already allocated to a student.");
            return;
        }
    
        // Update the project's student ID and status fields
        Student student=  StudentRepository.getByID(studentId);
        project.setStudentId(studentId);
        student.changeProjectId(projectId);
        project.setStatus(ProjectStatus.ALLOCATED);
        System.out.println("Project allocated to student.");
    }

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

    public void displayProjectByStatus(ProjectStatus status) {
        List<Project> matchingProjects = ProjectRepository.getProjectsByStatus(status);
        if (matchingProjects.isEmpty()) {
            System.out.println("No projects found with status " + status);
        } else {
            System.out.println("Projects with status: " + status);
            for (Project project : matchingProjects) {
                System.out.println("Project ID: " + project.getProjectId());
            }
        }
    }
    
    private static Scanner scanner = new Scanner(System.in);

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
                    System.out.println("Enter supervisor name filter:");
                    supervisorNameFilter = scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Enter student ID filter:");
                    studentIdFilter = scanner.next();
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }

            // Search for projects matching the selected filters
            List<Project> matchingProjects = ProjectRepository.searchProjects(statusFilter, studentIdFilter, supervisorNameFilter);

            // Print the details of the matching projects
            for (Project project : matchingProjects) {
                if (project.getStatus() == ProjectStatus.AVAILABLE || project.getStatus() == ProjectStatus.UNAVAILABLE) {
                    project.displayProjectID();
                    project.displaySupervisorInformation();
                    project.displayProjectInformation();
                    System.out.println("\n");
                } else if (project.getStatus() == ProjectStatus.ALLOCATED || project.getStatus() == ProjectStatus.RESERVED) {
                    project.displayProjectID();
                    project.displaySupervisorInformation();
                    project.displayStudentInformation();
                    System.out.println("\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }


    public void processPendingRequests() {
        List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);
    
        while (!pendingRequests.isEmpty()) {
            // Print all pending requests
            for (Request request : pendingRequests) {
                if(request.getType() == RequestType.transferStudent)
                    System.out.println(request.getRequestId() + " "+(SupervisorRepository.getByID(request.getFromId())).getName() + " request transfer student of project (id: " +request.getProjectId() + ") to " + request.getReplacementSupName()) ;
                else if(request.getType() == RequestType.assignProject){
                    System.out.println(request.getRequestId() + " " +(StudentRepository.getByID(request.getFromId())).getName() + " request to assign project (id:" +request.getProjectId() + ") to him ");
                }else if (request.getType() == RequestType.changeTitle){
                    System.out.println(request.getRequestId() + " " +(StudentRepository.getByID(request.getFromId())).getName() + " request to change title of project (id:" +request.getProjectId() + ") to " + request.getNewTitle());
                }else{
                    System.out.println(request.getRequestId() + " " +(StudentRepository.getByID(request.getFromId())).getName() + " request to deregister of project (id:" + request.getProjectId() + ")");
                }
            }
    
            // Process a request, ask for requestID
            System.out.println("Enter request ID to approve/reject or 0 to exit:");
            String requestId = scanner.next();
//            scanner.next(); // Consume the newline character
    
            if (requestId .equals("0") ) {
                break; // Exit loop
            }
    
            Request request = RequestRepository.getByID(String.valueOf(requestId));
            if (request != null) {
                System.out.println("Please select an option: \n" +
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
                            request.changeStatus(RequestStatus.Approved);
                            StudentRepository.getByID(request.getFromId()).changeStatus(StudentStatus.REGISTERED);
                            allocateProject(request.getProjectId(), request.getFromId());
                            for(Project project: ProjectRepository.searchProjects(null,null,ProjectRepository.getByID(request.getProjectId()).getSupervisorName())){
                                if(project.getStatus() == ProjectStatus.AVAILABLE){
                                    project.setStatus(ProjectStatus.UNAVAILABLE);
                                }
                            }
                            System.out.println("The request has been approved.");
                        } else {
                            System.out.println("The request has been rejected.");
                            request.changeStatus(RequestStatus.Rejected);
                        }
                        break;
                    case deregister:
                        if (processChoice == 1) {
                            request.changeStatus(RequestStatus.Approved);
                            StudentRepository.getByID(request.getFromId()).changeStatus(StudentStatus.DEREGISTERED);
                            deregisterStudentFromFYP(request.getProjectId());
                            System.out.println("The request has been approved.");
                        } else {
                            System.out.println("The request has been rejected.");
                            request.changeStatus(RequestStatus.Rejected);
                        }
                        break;
                    default:
                        System.out.println("Invalid request type.");
                        break;
                }
            } else {
                System.out.println("Request not found.");
            }
    
            // Update pending requests list
            pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);
        }
    
        if (pendingRequests.isEmpty()) {
            System.out.println("All pending requests processed.");
        }
    }

    //View all History
    public void viewRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();
    
        for (Request request : RequestRepository.getRequests()) {
            System.out.println("Request ID: " + request.getRequestId() + " Type: " + request.getType() + " From ID: " + request.getFromId() + " To ID: " + request.getToName() + " Status: " + request.getStatus());
        }
            /*
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToId())
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
        */
    }

}