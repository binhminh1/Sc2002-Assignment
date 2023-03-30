package model;

import java.util.Objects;
public class Coordinator extends User{
    private String userId;
    private String password = "password";
    private String email;
    private String name;

    public Coordinator(String userId, String name, String email) {
        super(userId, name, email);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = "password";
    }
    
    public void allocateProject(Project project, Student student) {
        if (project.getStatus() == ProjectStatus.AVAILABLE) {
            project.setStudent(student);
            project.setStatus(ProjectStatus.ALLOCATED);
            student.setProject(project);
        } else {
            throw new IllegalArgumentException("Project is not available for allocation");
        }
    }

    public void changeSupervisor(Project project, Supervisor supervisor) {
        project.setSupervisor(supervisor);
    }

    public void deregisterStudent(Student student) {
        Project project = student.getProject();
        if (project != null) {
            project.setStudent(null);
            project.setStatus(ProjectStatus.AVAILABLE);
            student.setProject(null);
            student.setDeregistered(true);
        } else {
            throw new IllegalArgumentException("Student is not registered for any project");
        }
    }

    public List<Project> getAvailableProjects() {
        List<Project> availableProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.AVAILABLE) {
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }

    public List<Project> getUnavailableProjects() {
        List<Project> unavailableProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.UNAVAILABLE) {
                unavailableProjects.add(project);
            }
        }
        return unavailableProjects;
    }

    public List<Project> getReservedProjects() {
        List<Project> reservedProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.RESERVED) {
                reservedProjects.add(project);
            }
        }
        return reservedProjects;
    }

    public List<Project> getAllocatedProjects() {
        List<Project> allocatedProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == ProjectStatus.ALLOCATED) {
                allocatedProjects.add(project);
            }
        }
        return allocatedProjects;
    }

    public List<Project> searchProjectsByStatus(ProjectStatus status) {
        List<Project> projectsByStatus = new ArrayList<>();
        for (Project project : projects) {
            if (project.getStatus() == status) {
                projectsByStatus.add(project);
            }
        }
        return projectsByStatus;
    }


}
