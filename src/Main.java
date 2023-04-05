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
                "If you are a Faculty, please enter 2 \n" +
                "If you are a  FYP coordinator, please enter 3 \n");
        int choice = sc.nextInt();
        //login as student
        switch (choice) {
            case 1:
                String studentuserid;
                while (true) {
                    System.out.println("Enter your user ID: ");
                    studentuserid = sc.nextLine();
                    Student student = StudentRepository.getByID(studentuserid);

                    if (student == null) {
                        System.out.println("Invalid user ID or password. Please try again.");
                        continue;
                    }
                    student.login(studentuserid, student);
                    break;
                }

                System.out.println("Welcome " + studentuserid + "!");
                System.out.println("Please select an option: \n" +
                        "1. changePassword \n" +
                        "2. View available projects \n" +
                        "3. Select the project to send to the coordinator\n" +
                        "4. View my project \n" +
                        "5. View requests status and history \n" +
                        "6. Request to change project title \n" +
                        "7. Request to deregister FYP \n");

                int studentChoice = sc.nextInt();
                Student student = StudentRepository.getByID(studentuserid);
                switch (studentChoice) {
                    case 1:
                        System.out.println("Please enter your new password: ");
                        String newPassword = sc.nextLine();
                        student.changePassword(newPassword);
                        System.out.println("Your password has been changed.");
                        student.login(studentuserid, student);
                        break;

                    case 2:
                        System.out.println("Available projects: ");
                        for (Project project : ProjectRepository.getAvailableProject()) {
                            System.out.println(project.getProjectId() + " " + project.getProjectTitle());
                        }
                        break;

                    case 3:
                        if (student.getStatus() == StudentStatus.UNREGISTERED) {
                            System.out.println("Please select a project to register: ");

                            //print all available projects
                            for (Project project : ProjectRepository.getAvailableProject()) {
                                System.out.println(project.getProjectId() + " " + project.getProjectTitle());
                            }
                            //student input the project id
                            String projectId = sc.nextLine();
                            Project project = ProjectRepository.getByID(projectId);
                            if (project != null) {
                                student.sendSelectProjectRequest(projectId, student.getUserId());

                                System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                            } else {
                                System.out.println("Invalid project ID. Please try again.");
                            }
                        }
                        break;

                    case 4:
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

                    case 5:
                        System.out.println("Your requests: ");
                        for (Request request : RequestRepository.getRequests()) {

                            System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());


                            System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
                        }
                        break;

                    case 6:
                        if (student.getStatus() == StudentStatus.REGISTERED) {
                            System.out.println("Please enter the new title of your project: ");
                            String newTitle = sc.nextLine();
                            student.sendChangeTitleRequest(student.getProjectId(), student.getSuperid(), newTitle);

                            System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                        } else {
                            System.out.println("You have not registered a project yet.");
                        }
                        break;

                    case 7:
                        if (student.getStatus() == StudentStatus.REGISTERED) {
                            System.out.println("Please select a project to deregister: ");
                            for (Project project : ProjectRepository.getProjects()) {
                                System.out.println(project.getProjectId() + " " + project.getProjectTitle());
                            }
                            String projectId = sc.nextLine();
                            Project project = ProjectRepository.getByID(projectId);
                            if (project != null) {
                                student.sendDeregisterProjectRequest(projectId, student.getUserId());
                                System.out.println("Your request has been sent. Please wait for the supervisor's approval.");
                            } else {
                                System.out.println("Invalid project ID. Please try again.");
                            }
                        } else {
                            System.out.println("You are unable to register or deregister a project at this moment.");
                        }
                        break;

                }
            case 2:
                String supervisoruserid;
                while (true) {
                    System.out.println("Enter your user ID: ");
                    supervisoruserid = sc.nextLine();
                    Supervisor supervisor = SupervisorRepository.getByID(supervisoruserid);

                    if (supervisor == null) {
                        System.out.println("Invalid user ID or password. Please try again.");
                        continue;
                    }
                    supervisor.login(supervisoruserid, supervisor);
                    break;
                }

                System.out.println("Welcome " + supervisoruserid + "!");
                System.out.println("Please select an option: \n" +
                        "1. changePassword \n" +
                        "2. Create/update/View projects \n" +//need another switch class
                        "3. Approve request\n" +
                        "4. Reject request\n" +
                        "5. View request history\n");

                        int supervisorChoice = sc.nextInt();
                        switch(supervisorChoice) {
                            case 1:
                                System.out.println("Please enter your new password: ");
                                String newPassword = sc.nextLine();
                                student.changePassword(newPassword);
                                System.out.println("Your password has been changed.");
                                student.login(studentuserid, student);
                                break;

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
                                Supervisor supervisor = SupervisorRepository.getByID(userId);
                                supervisor.processChangeTitleRequest();
                            case 4:

                            case 5:
                                System.out.println("Your requests: ");
                                for (Request request : RequestRepository.getRequests()) {
                                    if (Objects.equals(userId, request.getToId())) {
                                        System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
                                    }
                                    break;
                                }
                        }
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
         //               break;
                /*    case 4:
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
    */
