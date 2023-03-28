import model.*;
import model.Request;
import repository.ProjectRepository;
import repository.RequestRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;
import service.StudentService;

import java.io.IOException;
import java.util.Objects;
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
                    Student student = StudentRepository.getByID(userId);
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
                        "4. Check all my requests \n" +
                        "5. Change password \n" +
                        "6. Logout \n");
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
                                student.sendSelectProjectRequest(projectId, student.getUserId());
                                //


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
                            student.sendChangeTitleRequest(student.getProjectId(), student.getSuperid() , newTitle);
                            //


                            System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                        } else {
                            System.out.println("You have not registered a project yet.");
                        }
                        break;
                    case 4:
                        System.out.println("Your requests: ");
                        for (Request request : RequestRepository.getRequests()) {
                            System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
                        }
                        break;
                    case 5:
                        System.out.println("Please enter your new password: ");
                        String newPassword = sc.nextLine();
                        student.changePassword(newPassword);
                        System.out.println("Your password has been changed.");
                        break;
                    case 6:
                        System.out.println("You have logged out.");
                        break;
                }
            case 2:
                while (true) {
                    System.out.println("Enter your user ID: ");
                    userId = sc.nextLine();
                    System.out.println("Enter your password: ");
                    password = sc.nextLine();
                    Supervisor supervisor = SupervisorRepository.getByID(userId);
                    if (supervisor.login(userId, password)) {
                        break;
                    }
                    System.out.println("Invalid user ID or password. Please try again.");
                }
                Supervisor supervisor = SupervisorRepository.getByID(userId);
                System.out.println("Welcome " + userId + "!");
                System.out.println("Please select an option: \n" +
                        "1. View my projects \n" +
                        "2. View all my requests \n" +
                        "3. Process pending requests from students \n" +
                        "4. Transfer my project \n" +
                        "5. Create an project \n" +
                        "6. Change names of my project \n" +
                        "7. Change password \n" +
                        "8. Logout \n");
                int supervisorChoice = sc.nextInt();
                switch(supervisorChoice) {
                    case 1:
                        System.out.println("Your projects: ");
                        for (Project project : ProjectRepository.getProjects()) {
                            if (project.getSupervisorId().equals(SupervisorRepository.getByID(userId))) {
                                project.displayProject();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Your requests: ");
                        for (Request request : RequestRepository.getRequests()) {
                            if (Objects.equals(userId, request.getToId())) {
                                System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
                            }
                            break;
                        }
                    case 3:
                        System.out.println("Pending requests: ");
                        for (Request request : RequestRepository.getRequests()) {
                            if (request.getStatus() == RequestStatus.Pending) {
                                System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
                            }
                        }
                        System.out.println("Please select a request to process: ");
                        String requestId = sc.nextLine();
                        /*
                        Request request = RequestRepository.getByID(requestId);
                        if (request != null) {
                            System.out.println("Please select an option: \n" +
                                    "1. Approve \n" +
                                    "2. Reject \n");
                            int processChoice = sc.nextInt();
                            switch (processChoice) {
                                case 1:
                                    request.approve();
                                    System.out.println("The request has been approved.");
                                    break;
                                case 2:
                                    request.reject();
                                    System.out.println("The request has been rejected.");
                                    break;
                            }
                        } else {
                            System.out.println("Invalid request ID. Please try again.");
                        }

                         */
                        break;
                    case 4:
                        System.out.println("Please enter the new supervisor's user ID: ");
                        String newSupervisorId = sc.nextLine();
                        System.out.println("Please enter the project ID: ");
                        String proid = sc.nextLine();
                        Supervisor newSupervisor = SupervisorRepository.getByID(newSupervisorId);
                        if (newSupervisor != null) {
                            System.out.println("Please enter the project ID: ");
                            String projectId = sc.nextLine();
                            Project project = ProjectRepository.getByID(projectId);
                            if (project != null) {
                                supervisor.sendTransferStudentRequest(newSupervisorId, projectId);
                                //if succeed，change the supervisorId of the project
                                System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                            } else {
                                System.out.println("Invalid project ID. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid supervisor ID. Please try again.");
                        }
                        break;

                }
            case 3:
                while (true) {
                System.out.println("Enter your user ID: ");
                userId = sc.nextLine();
                System.out.println("Enter your password: ");
                password = sc.nextLine();
                String CoordinatorID = "ASFLI";
                Coordinator coordinator = new Coordinator(CoordinatorID,"Li Fang", "ASFLI@NTU.EDU.SG");

                if (coordinator.login(userId, password)){
                    break;
                }
                }
                System.out.println("Invalid user ID or password. Please try again.");
            }


        }
    }