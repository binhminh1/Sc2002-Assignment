package model;

import repository.ProjectRepository;
import repository.RequestRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
public class Supervisor extends User {

    public List<Project> projects = new ArrayList<>();

    public Supervisor(String userId, String name, String email) {
        super(userId, name, email);
    }

    Scanner scanner = new Scanner(System.in);

    /**
     * Allows supervisor to add new projects
     */
    public void addProjects(Project project) {
        projects.add(project);
    }

    /**
     * Allows supervisor to remove exisiting projects
     */
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
        for (Project project : ProjectRepository.searchProjects(null,null,super.getName())){
            project.displayProject();
        }
    }

    /**
     * Allows supervisor to send a request to coordinator to transfer student to another supervisor
     * @param supervisorId new supervisorID
     * @param ProjectId projectID does not change
     * @return
     */
    public boolean sendTransferStudentRequest(String supervisorId, String ProjectId) {
        Project project = ProjectRepository.getByID(ProjectId);
        if (project == null) {
            return false;
        }
        //projecttitle对应了supervisorName
        Supervisor supervisor = SupervisorRepository.getByName(project.getProjectTitle());
        if (supervisor == null) {
            return false;
        }
        Request request = new Request(RequestType.transferStudent, ProjectId, super.getUserId(), supervisorId);
        RequestRepository.addRequest(request);
        return true;
    }

    /**
     * Ensures that each supervisor only have 2 projects 
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

    //INCOMING request = change title
    public List<String> viewIncomingRequestsHistory() {
        List<String> requestHistory = new ArrayList<>();

        for (Request request : RequestRepository.getRequestsByToName(this.getUserId())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request ID: ").append(request.getRequestId())
                    .append("\nType: ").append(request.getType())
                    .append("\nProject ID: ").append(request.getProjectId())
                    .append("\nFrom ID: ").append(request.getFromId())
                    .append("\nTo ID: ").append(request.getToName())
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
                    .append("\nTo ID: ").append(request.getToName())
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

    /**
     * Approve change title request
     */
    public void changeTitle(String newTitle, String projectId) {
        Project project =   ProjectRepository.getByID(projectId);
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
                    System.out.println(request.getRequestId() + " from: " + request.getFromId() + " " +  request.getType() + " " + request.getStatus());
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
                    req=request1;
                }
            }
            if (req== null) {
                System.out.println("Invalid request ID");
                return;
            }
            // Process a request
            if (processChoice == 1) {
                req.changeStatus(RequestStatus.Approved);
                System.out.println("Request approved");
                Student student = StudentRepository.getByID(studentId);
                //change title
                changeTitle(req.getNewTitle(), student.getProjectId());
            } else if (processChoice == 2) {
                req.changeStatus(RequestStatus.Rejected);
                System.out.println("Request rejected");
            } else {
                System.out.println("Invalid choice");
            }


                /*

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
                    scanner.next();// Consume the newline character
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
            }*/
        }
    }
}
