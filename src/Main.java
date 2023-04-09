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

import static model.StudentStatus.PENDING;
import static model.StudentStatus.UNREGISTERED;

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
                String studentuserid = null;
                Boolean result = false;
                while (!result) {
                    System.out.println("Enter your user ID: ");
                    studentuserid = sc.next();
                    Student student = StudentRepository.getByID(studentuserid);

                    if (student == null) {
                        System.out.println("Invalid user ID or password. Please try again.");
                        continue;
                    }
                    result = student.login(studentuserid, student);
                }

                while (true) {
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
                            String newPassword = sc.next();
                            student.changePassword(newPassword);
                            System.out.println("Your password has been changed.");
                            student.login(studentuserid, student);
                            break;

                        case 2:
                            System.out.println("Available projects: ");
                            for (Project project : ProjectRepository.getAvailableProject()) {
                                System.out.println(project.getProjectId() + " " + project.getProjectTitle() + " " + project.getSupervisorId());
                            }
                            break;

                        case 3:
                            if (student.getStatus() == UNREGISTERED) {
                                System.out.println("Please select a project to register: ");

                                //print all available projects
                                for (Project project : ProjectRepository.getAvailableProject()) {
                                    System.out.println(project.getProjectId() + " " + project.getProjectTitle() + " " + project.getSupervisorId());
                                }
                                //student input the project id
                                String projectId = sc.next();
                                Project project = ProjectRepository.getByID(projectId);
                                if (project != null) {
                                    student.sendSelectProjectRequest(projectId, student.getUserId());

                                    System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
                                    student.changeStatus(PENDING);
                                } else {
                                    System.out.println("Invalid project ID. Please try again.");
                                }
                            } else {
                                System.out.println("You are unable to register or deregister a project at this moment.");
                            }
                            break;

                        case 4:
                            if (student.getStatus() == StudentStatus.REGISTERED) {
                                for (Project project : ProjectRepository.getAvailableProject()) {
                                    if (project.getStudentId().equals(student.getUserId())) {
                                        System.out.println(project.getProjectId() + " " + project.getProjectTitle() + " " + project.getSupervisorId());
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

                            }
                            break;

                        case 6:
                            if (student.getStatus() == StudentStatus.REGISTERED) {
                                System.out.println("Please enter the new title of your project: ");
                                String newTitle = sc.next();
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
                                String projectId = sc.next();
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
                }
            case 2:
                String supervisoruserid = null;
                boolean superResult = false;
                while (!superResult) {
                    System.out.println("Enter your user ID: ");
                    supervisoruserid = sc.next();
                    Supervisor supervisor = SupervisorRepository.getByID(supervisoruserid);

                    if (supervisor == null) {
                        System.out.println("Invalid user ID or password. Please try again.");
                        continue;
                    }
                    superResult = supervisor.login(supervisoruserid, supervisor);
                }
                loop1:
                while (true) {
                    Supervisor supervisor = SupervisorRepository.getByID(supervisoruserid);
                    System.out.println("Welcome " + supervisoruserid + "!");
                    System.out.println("Please select an option: \n" +
                            "1. changePassword \n" +
                            "2. Create/update/View projects \n" +//need another switch class
                            "3. ViewStudentPendingRequest \n" +
                            "4. View request history\n" +
                            "5. Request to transfer student \n" +
                            "6. back\n");

                    int supervisorChoice = sc.nextInt();
                    switch (supervisorChoice) {
                        case 1:
                            System.out.println("Please enter your new password: ");
                            String newPassword = sc.next();
                            supervisor.changePassword(newPassword);
                            System.out.println("Your password has been changed.");
                            supervisor.login(supervisoruserid, supervisor);
                            System.out.println("Your projects: ");
                            for (Project project : ProjectRepository.getProjects()) {
                                if (project.getSupervisorId().equals(SupervisorRepository.getByID(supervisoruserid))) {
                                    project.displayProject();
                                }
                            }
                            break;
                        case 2:
                            loop2:
                            while (true) {
                                System.out.println("Please select an option: \n" +
                                        "1. create \n" +
                                        "2. update \n" +//need another switch class
                                        "3. view \n" +
                                        "4. back \n");
                                int projectChoice = sc.nextInt();
                                switch (projectChoice) {
                                    case 1:
                                        if (supervisor.supervisorCapReached(supervisoruserid)) {
                                            System.out.println("You already have two projects");
                                            continue;
                                        }
                                        System.out.println("Please enter the project  name");
                                        String projectName = sc.next();
                                        Project project = new Project("1", projectName, supervisor.getUserId());
                                        supervisor.addProjects(project);
                                        ProjectRepository.addProject(project);
                                        break;

                                    case 2:
                                        supervisor.viewProjects();
                                        System.out.println("choose the project you want to update by id");
                                        String projectId = sc.next();
                                        System.out.println("Please enter the new title");
                                        String newTitle = sc.next();
                                        if (supervisor.getProjectsById(projectId) != null) {
                                            supervisor.changeTitle(newTitle, projectId);
                                            System.out.println("successfully changed");
                                        } else {
                                            System.out.println("invalid projectId");
                                        }
                                        break;
                                    case 3:
                                        supervisor.viewProjects();
                                        break;
                                    case 4:
                                        break loop2;

                                }
                                break;
                            }
                        case 3:
                            supervisor.processChangeTitleRequest();
                            break;
                        case 4:
                            System.out.println("Your requests: ");
                            for (Request request : RequestRepository.getRequests()) {
                                if (Objects.equals(supervisor.getUserId(), request.getToId())) {
                                    System.out.println(request.getRequestId() + " " + request.getType() + " " + request.getStatus());
                                }
                            }
                            break;
                        case 5:
                            System.out.println("Please enter the project ID: ");
                            String id = sc.next();
                            System.out.println("Please enter the supervisor ID: ");
                            String supervisorId = sc.next();

                          String projectId = id;
                            Supervisor trasferSuper= SupervisorRepository.getByID(supervisorId);
                            if (trasferSuper==null) {
                                System.out.println("Invalid supervisor ID. Please try again.");
                                break;
                            }
                           boolean re= trasferSuper.sendTransferStudentRequest( supervisorId, projectId);
                            if(!re){
                                System.out.println("Invalid project ID. Please try again.");
                                break;
                            }
                            System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
                            break;
                        case 6:
                            break loop1;


                    }
                }
                /*
            case 3:
                while (true) {
                System.out.println("Enter your user ID: ");
                userId = sc.next();
                System.out.println("Enter your password: ");
                password = sc.next();
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
        }
    }
}