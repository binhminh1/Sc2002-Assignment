import model.*;
import model.Request;
import repository.ProjectRepository;
import repository.RequestRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;

import java.io.IOException;
import java.util.Scanner;

import static model.StudentStatus.PENDING;
import static model.StudentStatus.UNREGISTERED;

public class Main {

    private static final String CoordinatorID = "ASFLI";

    public static void main(String[] args) throws IOException {


        String userId;
        String password;
        Scanner sc = new Scanner(System.in);


        System.out.println("Welcome to FYPMS! Please wait a few seconds for initialization");
        ReadCSV.readFile();
        boolean exit=false;
        while (true) {
            System.out.println("If you are a student, please enter 1 \n" +
                    "If you are a Faculty, please enter 2 \n" +
                    "If you are a FYP coordinator, please enter 3 \n");
            int choice = sc.nextInt();
            //login as student
            switch (choice) {
                case 1:
                    String studentuserid = null;
                    Boolean result = false;
                    Boolean exit2 = false;
                    while (!result) {

                        System.out.println("Enter your user ID: ");
                        System.out.println("Enter back to go back");
                        studentuserid = sc.next();

                        if(studentuserid.equals("back")){
                            exit2 = true;
                            break;
                        }
                        Student student = StudentRepository.getByID(studentuserid);
                        if (student == null) {
                            System.out.println("Invalid user ID or password. Please try again.");
                            continue;
                        }
                        result = student.login(studentuserid, student);
                    }
                    if(exit2){
                        break;
                    }
                    int studentChoice = 0;
                    while (studentChoice != 8) {
                        System.out.println("Welcome " + studentuserid + "!");
                        System.out.println("Please select an option: \n" +
                                "1. changePassword \n" +
                                "2. View available projects \n" +
                                "3. Select the project to send to the coordinator\n" +
                                "4. View my project \n" +
                                "5. View requests status and history \n" +
                                "6. Request to change project title \n" +
                                "7. Request to deregister FYP \n" +
                                "8. Exit \n");
                        studentChoice = sc.nextInt();
                        Student student = StudentRepository.getByID(studentuserid);
                        switch (studentChoice) {
                            case 1:
                                student.ChangePassword(student, studentuserid);
                                break;

                            case 2:
                                if(student.getStatus().equals(StudentStatus.DEREGISTERED)){
                                    System.out.println("You are not allowed to make selection again as you deregistered your FYP");
                                    break;
                                }
                                System.out.println("Available projects: ");
                                for (Project project : ProjectRepository.getAvailableProject()) {
                                    project.displayProject();

                                }
                                break;

                            case 3:
                                Request re = student.sendSelectProjectRequest(student);
                                if (re != null) {
                                    System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
                                    student.changeStatus(PENDING);
                                }
                                break;
                            case 4:
                                student.viewMyProject(student);
                                break;
                            case 5:
                                System.out.println("Your requests: ");
                                student.viewRequestHistory(studentuserid);
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
                                    student.sendDeregisterProjectRequest(student.getProjectId());
                                    System.out.println("Your request has been sent. Please wait for the coordinator's approval.");

                                } else {

                                    System.out.println("You are unable to deregister a project at this moment.");
                                }
                                break;
                            case 8:
                                exit=true;
                                break;
                            default:
                                System.out.println("Please enter a number between 1 - 8. Please try again.");
                                break;
                        }
                    }
                    if(exit){
                        break;
                    }
                case 2:
                    String supervisoruserid = null;
                    boolean superResult = false;
                    boolean exit3 = false;
                    while (!superResult) {
                        System.out.println("Enter your user ID: ");
                        System.out.println("Enter back to go back");
                        supervisoruserid = sc.next();
                        if(supervisoruserid.equals("back")){
                            exit3 = true;
                            break;
                        }
                        Supervisor supervisor = SupervisorRepository.getByID(supervisoruserid);

                        if (supervisor == null) {
                            System.out.println("Invalid user ID or password. Please try again.");
                            continue;
                        }
                        superResult = supervisor.login(supervisoruserid, supervisor);
                    }
                    if(exit3){
                        break;
                    }
                    int supervisorChoice = 0;

                    while (supervisorChoice != 6) {
                        Supervisor supervisor = SupervisorRepository.getByID(supervisoruserid);
                        System.out.println("Welcome " + supervisoruserid + "!");
                        System.out.println("Please select an option: \n" +
                                "1. changePassword \n" +
                                "2. Create/update/View projects \n" +//need another switch class
                                "3. View Student Pending Request \n" +
                                "4. View request history\n" +
                                "5. Request to transfer student \n" +
                                "6. Exit\n");

                        supervisorChoice = sc.nextInt();
                        switch (supervisorChoice) {
                            case 1:
                                supervisor.ChangePassword(supervisor, supervisoruserid);
                                break;
                            case 2:

                                while (true) {
                                    System.out.println("Please select an option: \n" +
                                            "1. create \n" +
                                            "2. update \n" +//need another switch class
                                            "3. view \n" +
                                            "4. back \n");
                                    int projectChoice = sc.nextInt();
                                    switch (projectChoice) {
                                        case 1:
                                            System.out.println("Please enter the project  name");
                                            String projectName = sc.next();
                                            Project project = new Project(String.valueOf(ProjectRepository.numberOfProjects + 1), supervisor.getName(),projectName);
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
                                            break;
                                        default:
                                            System.out.println("Please enter a number between 1 - 4. Please try again.");
                                            break;
                                    }
                                    break;
                                }
                                break;
                            case 3:
                                supervisor.processChangeTitleRequest();
                                break;
                            case 4:
                                System.out.println("Your requests: ");
                                supervisor.viewRequestHistory(supervisoruserid, supervisor);
                                break;
                            case 5:
                                while(true) {
                                    supervisor.viewProjects();
                                    System.out.println("");
                                    System.out.println("Please enter the project ID: ");
                                    String projectId = sc.next();
                                    System.out.println("Please enter the replacement supervisor name: ");
                                    sc.nextLine(); // consume the end-of-line character
                                    String supervisorNameToTransfer = sc.nextLine();

                                    Supervisor trasferSuper = SupervisorRepository.getByName(supervisorNameToTransfer);
                                    if (trasferSuper == null) {
                                        System.out.println("Invalid supervisor name. Please try again.");
                                        break;
                                    }
                                    boolean ra = supervisor.sendTransferStudentRequest(supervisorNameToTransfer, projectId);
                                    if(ra){
                                        break;
                                    }
                                }
                                System.out.println("Your request has been sent. Please wait for the coordinator's approval.");
                                break;
                            case 6:
                                exit=true;
                                break;
                            default:
                                System.out.println("Please enter a number between 1 - 6. Please try again.");
                                break;
                        }
                    }
                    if (exit) {
                        break;
                    }


                case 3:
                    boolean coordinatorResult = false;
                    String coorId = null;
                    boolean exit4 = false;
                    while (!coordinatorResult) {
                        System.out.println("Enter your user ID: ");
                        System.out.println("Enter back to go back");
                        coorId = sc.next();
                        if(coorId.equals("back")){
                            exit4 = true;
                            break;
                        }

                        if (!coorId.equals("ASFLI")) {
                            System.out.println("Invalid user ID or password. Please try again.");
                            continue;
                        }
                        Coordinator coordinator = new Coordinator("ASFLI", "Li Fang", "ASFLI@NTU.EDU.SG");
                        coordinatorResult = coordinator.login(coorId, coordinator);
                    }
                    if(exit4){
                        break;
                    }
                    Coordinator coordinator = new Coordinator("ASFLI", "Li Fang", "ASFLI@NTU.EDU.SG");
                    int coorChoice = 0;
                    while (coorChoice != 5) {
                        System.out.println("Welcome " + coordinator.getName() + "!");
                        System.out.println("Please select an option: \n" +
                                "1. changePassword \n" +
                                "2. View Projects By filter \n" +//need another switch class
                                "3. View Request \n" +
                                "4. View request history\n" +
                                "5. Exit\n");
                        coorChoice = sc.nextInt();
                        switch (coorChoice) {
                            case 1:
                                coordinator.ChangePassword(coordinator, coorId);
                                break;
                            case 2:
                                coordinator.displayReportByFilters();
                                break;
                            case 3:
                                coordinator.processPendingRequests();
                                break;
                            case 4:
                                coordinator.viewRequestsHistory();
                                break;
                            case 5:
                                exit = true;
                                break;
                            default:
                                System.out.println("Please enter a number between 1 - 5. Please try again.");
                                break;
                        }
                    }
                    if (exit) {
                        break;
                    }
            }
        }
    }
}

