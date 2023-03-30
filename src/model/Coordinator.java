package model;
 
import java.util.Objects;
import repository.ProjectRepository;
import repository.SupervisorRepository;
import repository.StudentRepository; 
import java.util.ArrayList;
import java.util.List;

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

    public void changeProjectSupervisor(String projectId, String newSupervisorId) {
        Project projectToUpdate = ProjectRepository.getByID(projectId);
        if (projectToUpdate != null) {
            projectToUpdate.setSupervisorId(newSupervisorId);
            System.out.println("Project supervisor updated.");
        } else {
            System.out.println("Project not found.");
        }
    }

    public void allocateProject(String projectId, String studentId) {
        Project project = ProjectRepository.getByID(projectId);
    
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
    
        // Check if the project is already allocated to a student
        if (project.getStudentId() != null) {
            System.out.println("Project is already allocated to a student.");
            return;
        }
    
        // Update the project's student ID and status fields
        project.setStudentId(studentId);
        project.setStatus(ProjectStatus.ALLOCATED);
        System.out.println("Project allocated to student.");
    }

    public void deregisterStudentFromFYP(String projectId) {
        Project projectToUpdate = ProjectRepository.getByID(projectId);
    
        if (projectToUpdate != null) {
            String studentId = projectToUpdate.getStudentId();
            projectToUpdate.setStudentId(null);
            projectToUpdate.setStatus(ProjectStatus.AVAILABLE);
            System.out.println("Project deregistered from student.");
            
            // Update student status
            Student studentToUpdate = StudentRepository.getByID(studentId);
            if (studentToUpdate != null) {
                studentToUpdate.changeStatus(StudentStatus.DEREGISTERED);
                System.out.println("Student deregistered from FYP.");
            } else {
                System.out.println("Student not found.");
            }
        } else {
            System.out.println("Project not found.");
        }
    } 

    public void displayProjectByStatus(ProjectStatus status) {
        List<Project> matchingProjects = ProjectRepository.getProjectsByStatus(status);
        if (matchingProjects.isEmpty()) {
            System.out.println("No projects found with status " + status);
        } else {
            System.out.println("Projects with status: " + status);
            for (Project project : matchingProjects) {
                System.out.println("Project ID: " + project.getProjectId());
            }
        }
    }
    

}
