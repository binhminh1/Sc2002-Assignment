package model;

import repository.ProjectRepository;
import repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Supervisor extends User {
    private String toId;
    public List<Project> projects = new ArrayList<>();
    public Supervisor(String userId, String name, String email) {
        super(userId, name, email);

    }
    public void addProjects(Project project){
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
}