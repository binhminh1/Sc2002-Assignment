package model;
 
import java.util.Objects;
import java.util.Scanner;

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
    
    private static Scanner scanner = new Scanner(System.in);

    private void displayReportByFilters() {
        // Ask coordinator for filter options
        System.out.println("Please select filter options:");
        System.out.println("1. Project status");
        System.out.println("2. Supervisor ID");
        System.out.println("3. Student ID");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Get selected filter values
        ProjectStatus statusFilter = null;
        String supervisorIdFilter = null;
        String studentIdFilter = null;
        switch (choice) {
            case 1:
                System.out.println("Enter project status filter (UNAVAILABLE, AVAILABLE, RESERVED, or ALLOCATED):");
                statusFilter = ProjectStatus.valueOf(scanner.nextLine().toUpperCase());
                break;
            case 2:
                System.out.println("Enter supervisor ID filter:");
                supervisorIdFilter = scanner.nextLine();
                break;
            case 3:
                System.out.println("Enter student ID filter:");
                studentIdFilter = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        // Search for projects matching the selected filters
        List<Project> matchingProjects = ProjectRepository.searchProjects(statusFilter, supervisorIdFilter, studentIdFilter);

        // Print the details of the matching projects
        for (Project project : matchingProjects) {
            if (statusFilter == ProjectStatus.AVAILABLE || statusFilter == ProjectStatus.UNAVAILABLE ) {
                project.displayProjectID();
                project.displaySupervisorInformation();
                project.displayProjectInformation();
            } else if (statusFilter == ProjectStatus.ALLOCATED||statusFilter == ProjectStatus.RESERVED) {
                project.displayProjectID();
                project.displaySupervisorInformation();
                project.displayStudentInformation();
                project.displayProjectInformation();;
            }           
        }
    }


}