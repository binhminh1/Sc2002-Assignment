import model.*;
import model.Request;
import repository.ProjectRepository;
import repository.RequestRepository;
import service.ProjectService;
import service.StudentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        String userId;
        String password;
        Scanner sc = new Scanner(System.in);


        System.out.println("Welcome to FYPMS! Please wait a few seconds for initialization");
        ReadCSV.readFile();
        System.out.println("If you are a student, please enter 1 \n" +
                "If you are a supervisor, please enter 2 \n" +
                "If you are a coordinator, please enter 3 \n");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                while (true) {
                    System.out.println("Enter your user ID: ");
                    userId = sc.nextLine();
                    System.out.println("Enter your password: ");
                    password = sc.nextLine();
                    Student student = StudentService.getByID(userId);
                    //The static method login(String, String) from the type User should be accessed in a static way
                    if (student.login(userId, password)) {
                        break;
                    }
                    System.out.println("Invalid user ID or password. Please try again.");
                }

                Student student = StudentService.getByID(userId);
                System.out.println("Welcome " + userId + "!");
                System.out.println("Please select an option: \n" +
                        "1. Register/Deregister \n" +
                        "2. View my project \n" +
                        "3. Change the title of my project \n" +
                        "4. Check my requests \n" +
                        "5.Change password \n" +
                        "6.Logout \n");
                int studentChoice = sc.nextInt();
                switch (studentChoice) {
                    case 1:
                        if (student.getStatus() == StudentStatus.UNREGISTERED) {
                            System.out.println("Please select a project to register: ");
                            for (Project project : ProjectRepository.getAvailableProject()) {
                                System.out.println(project.getProjectId() + " " + project.getProjectTitle());
                            }
                            String projectId = sc.nextLine();
                            Project project = ProjectRepository.getByID(projectId);
                            if (project != null) {
                                student.sendSelectProjectRequest(projectId, student.getUserId(), null);
                                System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                            } else {
                                System.out.println("Invalid project ID. Please try again.");
                            }
                        } else if (student.getStatus() == StudentStatus.REGISTERED) {
                            System.out.println("Please select a project to deregister: ");
                            for (Project project : ProjectRepository.getProjects()) {
                                System.out.println(project.getProjectId() + " " + project.getProjectTitle());
                            }
                            String projectId = sc.nextLine();
                            Project project = ProjectRepository.getByID(projectId);
                            if (project != null) {
                                student.sendDeregisterProjectRequest(projectId, student.getUserId(), null);
                                System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                            } else {
                                System.out.println("Invalid project ID. Please try again.");
                            }
                        } else {
                            System.out.println("You are unable to register or deregister a project at this moment.");
                        }
                        break;
                    case 2:
                        if (student.getStatus() == StudentStatus.REGISTERED) {
                            for (Project project : ProjectRepository.getAvailableProject()) {
                                if (project.getStudentId().equals(student.getUserId())) {
                                    System.out.println(project.getProjectId() + " " + project.getProjectTitle());
                                }
                            }
                        } else {
                            System.out.println("You have not registered a project yet.");
                        }
                        break;
                    case 3:
                        if (student.getStatus() == StudentStatus.REGISTERED) {
                            System.out.println("Please enter the new title of your project: ");
                            String newTitle = sc.nextLine();
                            student.sendChangeTitleRequest(newTitle, student.getUserId(), null);
                            System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                        } else {
                            System.out.println("You have not registered a project yet.");
                        }
                        break;
                    case 4:
                        System.out.println("Your requests: ");
                        for (Request request : RequestRepository.getRequests()) {
                            System.out.println(request.getRequestId() + " " + request.getRequestType() + " " + request.getStatus()); 
                            
                        }


                }


            case 2:
                System.out.println("Enter your user ID: ");
                userId = sc.nextLine();
                System.out.println("Enter your password: ");
                password = sc.nextLine();
                if (User.login(userId, password))
                    System.out.println("Welcome " + userId + "!");
                break;
            case 3:
                System.out.println("Enter your user ID: ");
                userId = sc.nextLine();
                System.out.println("Enter your password: ");
                password = sc.nextLine();
                if (User.login(userId, password))
                    System.out.println("Welcome " + userId + "!");
                break;


        }
    }
}