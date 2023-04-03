package model;

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

    public boolean sendTransferStudentRequest(String supervisorId, String ProjectId) {
        Request request = new Request(RequestType.transferStudent, ProjectId, super.getUserId(), supervisorId);
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
    public void changeTitle(String newTitle, String projectId) {
        for (Project project : projects) {
            if (Objects.equals(project.getProjectId(), projectId)) {
                project.setProjectTitle(newTitle);
            }
        }
    }

    public void processChangeTitleRequest(Request request) {
        List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);

        while (!pendingRequests.isEmpty()) {
            // Print all pending requests
            for (Request request1 : pendingRequests) {
                if (request1.getType() == (RequestType.changeTitle) && request1.getToId().equals(super.getUserId())) {
                    System.out.println(request1.getRequestId() + " " + request1.getType() + " " + request1.getStatus());
                }
            }
            // Process a request
            System.out.println("Enter request ID to approve/reject or 0 to exit:");
            int requestId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (requestId == 0) {
                break; // Exit loop
            }
            Request request1 = RequestRepository.getByID(String.valueOf(requestId));
            int processChoice;
            if (request1 != null) {
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
                request1.changeStatus(RequestStatus.Approve);
                System.out.println("Request approved");

                //change title
                changeTitle(request1.getNewTitle(), request1.getProjectId());

            } else if (processChoice == 2) {
                request1.changeStatus(RequestStatus.Reject);
                System.out.println("Request rejected");
            } else {
                System.out.println("Invalid option");
            }
        }
    }
}