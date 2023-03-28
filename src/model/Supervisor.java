package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Supervisor extends User {
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

    public Request sendTransferStudentRequest(String studentId, String supervisorId, String ProjectId){
        Request request = new Request(RequestType.transferStudent, studentId, supervisorId, ProjectId);
        return request;
    }
}