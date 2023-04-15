package repository;

import model.Project;
import model.ProjectStatus;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {
    /**
     * Declaration of variables
     */
    public static int numberOfProjects = 0;
    /**
     * Creates list of projects
     */
    public static List <Project> projects = new ArrayList<>();

    /**
     * Allow us to add a project
     * @param project
     */
    public static void addProject(Project project) {
        numberOfProjects++;
        projects.add(project);
    }

    /**
     * Allows us to remove a project
     * @param project
     */
    public static void removeProject(Project project) {
        projects.remove(project);
    }

    /**
     * @return projects
     */
    public static List<Project> getProjects() {
        return projects;
    }

    /**
     * get the project ID
     * @param id
     * @return
     */
    public static Project getByID(String id) {
        for (Project project : projects) {
            if (project.getProjectId().equals(id)) {
                return project;
            }
        }
        return null;
    }
    
    /**
     * @return all of the available projects
     */
    public static List<Project> getAvailableProject() {
        List<Project> availableProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.AVAILABLE) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }

    /**
     * categorise projects by status
     * @param status (AVAILABLE, UNAVAILABLE, RESERVED, ALLOCATED)
     * @return
     */
    public static List<Project> getProjectsByStatus(ProjectStatus status) {
        List<Project> matchingProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == status) {
                matchingProjects.add(project);
            }
        }
        return matchingProjects;
    }

    /**
     * allow coordinator to search for a project by its characteristics
     * @param status (AVAILABLE, UNAVAILABLE, RESERVED, ALLOCATED)
     * @param studentId
     * @param supervisorName
     * @return
     */
    public static List<Project> searchProjects(ProjectStatus status, String studentId, String supervisorName) {
        List<Project> matchingProjects = new ArrayList<>();
        for (Project project : projects) {
            boolean matchesStatus = (status == null || project.getStatus() == status);

            boolean matchesStudentId = (studentId == null || (project.getStudentId() != null && project.getStudentId().equals(studentId)));

            boolean matchesSupervisorName = (supervisorName == null || (project.getSupervisorName() != null && project.getSupervisorName().equals(supervisorName)));

            if (matchesStatus && matchesStudentId && matchesSupervisorName) {
                matchingProjects.add(project);
            }
        }
        return matchingProjects;
    }



}
