package model;

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
        Request request = new Request(RequestType.transferStudent , ProjectId , super.getUserId() , supervisorId);
        RequestRepository.addRequest(request);
        return true;
    }
}