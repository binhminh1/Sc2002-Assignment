package repository;

import model.Project;
import model.ProjectStatus;

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

    public static List<Project> getProjectsByStatus(ProjectStatus status) {
        List<Project> matchingProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == status) {
                matchingProjects.add(project);
            }
        }
        return matchingProjects;
    }

    public static List<Project> searchProjects(ProjectStatus status, String studentId, String supervisorName) {
        List<Project> matchingProjects = new ArrayList<>();
        for (Project project : projects) {
            boolean matchesStatus = (status == null || project.getStatus() == status);
            boolean matchesStudentId = (studentId == null || (project.getStudentId() != null && project.getStudentId().equals(studentId)));
            boolean matchesSupervisorName = (supervisorName == null || (project.getSupervisorName() != null && project.getSupervisorName().equalsIgnoreCase(supervisorName)));

            if (matchesStatus && matchesStudentId && matchesSupervisorName) {
                matchingProjects.add(project);
            }
        }
        return matchingProjects;
    }



}
