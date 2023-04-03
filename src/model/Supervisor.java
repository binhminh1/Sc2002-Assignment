package model;

import repository.ProjectRepository;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class Supervisor extends User {
    private String toId;
    public List<Project> projects = new ArrayList<>();

    public Supervisor(String userId, String name, String email) {
        super(userId, name, email);

    }
    Scanner scanner = new Scanner(System.in);

    public void addProjects(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public List<Project> getProjects() {
        return projects;
    }


    public boolean sendTransferStudentRequest(String supervisorId,String ProjectId){
        Project project = ProjectRepository.getByID(ProjectId);
        if( project != null ||  project.getSupervisorId() != this.getUserId()){return false;}
        Request request = new Request(RequestType.transferStudent , ProjectId , super.getUserId() , supervisorId);
        RequestRepository.addRequest(request);
        return true;
    }

    public boolean supervisorCapReached(String newSupervisorId) {
        int numOfProject = 0;
        for (Project project : projects) {
            if (Objects.equals(project.getSupervisorId(), newSupervisorId)) {
                numOfProject++;
            }
        }
        return numOfProject >= 2;
    }    


    public void viewRequestHistory() {
        List<Request> incomingRequests = RequestRepository.getRequestsBygetToId(this.getUserId());
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
    
    //INCOMING request = change title
    public List<String> viewIncomingRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();
    
        for (Request request : RequestRepository.getRequestsBygetToId(this.getUserId())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nProject ID: ").append(request.getProjectId())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToId())
                    .append("\nNew title: ").append(request.getNewTitle())
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


    //OUTGOING request = transfer student
    public List<String> viewOutgoingRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();
    
        for (Request request : RequestRepository.getRequestsByFromId(this.getUserId())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nProject ID: ").append(request.getProjectId())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToId())
                    .append("\nReplacement supervisor: ").append(request.getReplacementSupId())
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
    

    public void changeTitle(String newTitle, String projectId) {
        for (Project project : projects) {
            if (Objects.equals(project.getProjectId(), projectId)) {
                project.setProjectTitle(newTitle);
            }
        }
    }

        public void changeTitle (String newTitle, String projectId){
            for (Project project : projects) {
                if (Objects.equals(project.getProjectId(), projectId)) {
                    project.setProjectTitle(newTitle);
                }
            }
        }

        public void processChangeTitleRequest (Request request){
            List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);

            while (!pendingRequests.isEmpty()) {
                // Print all pending requests
                for (Request request1 : pendingRequests) {
                    if (request1.getType() == (RequestType.changeTitle) && request1.getToId().equals(super.getUserId())) {
                        System.out.println(request1.getRequestId() + " " + request1.getType() + " " + request1.getStatus());
                    }
                }
                // Process a request
                System.out.println("Enter student ID to approve/reject or 0 to exit:");
                int studentId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (studentId == 0) {
                    break; // Exit loop
                }
                // request2 is the request that the supervisor wants to process
                Request request2 = null;
                for (Request request1 : pendingRequests) {
                    if (Objects.equals(request1.getFromId(), studentId)) {
                        request2 = request1;
                    }
                }
                int processChoice;
                if (request2 != null) {
                    System.out.println("Please select an option: \n" +
                            "1. Approve \n" +
                            "2. Reject \n");
                    processChoice = scanner.nextInt();
                    scanner.nextLine();// Consume the newline character
                } else {
                    System.out.println("Invalid request ID");
                    continue;
                }

                if (processChoice == 1) {
                    request2.changeStatus(RequestStatus.Approve);
                    System.out.println("Request approved");

                    //change title
                    changeTitle(request2.getNewTitle(), request2.getProjectId());

                } else if (processChoice == 2) {
                    request2.changeStatus(RequestStatus.Reject);
                    System.out.println("Request rejected");
                } else {
                    System.out.println("Invalid option");
                }
            }
        }
}