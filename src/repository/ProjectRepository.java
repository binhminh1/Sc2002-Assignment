package repository;

import model.Project;
import model.ProjectStatus;
import model.Student;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    public static List <Project> projects = new ArrayList<>();

    public static void addProject(Project project) {
        projects.add(project);
    }

    public static void removeProject(Project project) {
        projects.remove(project);
    }

    public static List<Project> getProjects() {
        return projects;
    }

    public static Project getByID(String id) {
        for (Project project : projects) {
            if (project.getProjectId().equals(id)) {
                return project;
            }
        }
        return null;
    }

    public static List<Project> getAvailableProject() {
        List<Project> availableProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.AVAILABLE) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }
}
