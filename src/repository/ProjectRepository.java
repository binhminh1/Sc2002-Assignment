package repository;

import model.Project;
import model.Student;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    public List<Project> projects = new ArrayList<>();

    public void addProject(Project project) {
        projects.add(project);
    }

    public void removeProject(Project project) {
        projects.remove(project);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getByID(String id) {
        for (Project project : projects) {
            if (project.getProjectId().equals(id)) {
                return project;
            }
        }
        return null;
    }
}
