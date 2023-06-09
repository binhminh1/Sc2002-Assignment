import model.*;
import model.Request;
import repository.RequestRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;
/**
 * Output and UI of FYPMS
 * Switch cases allows users to choose what they want to do
 * Exception handling implemented to catch error inputs
 */
public class Main {

    private static final String CoordinatorID = "ASFLI";

    public static void main(String[] args) throws IOException {


        String userId;
        String password;
        Scanner sc = new Scanner(System.in);


        System.out.println("\nWelcome to FYPMS! Please wait a few seconds for initialization");
        ReadCSV.readFile();
        boolean exit = false;
        System.out.println("" +
                " ▄▄▄▄▄▄▄ ▄▄   ▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄ ▄▄▄▄▄▄▄ \n" +
                "█       █  █ █  █       █  █▄█  █       █\n" +
                "█    ▄▄▄█  █▄█  █    ▄  █       █  ▄▄▄▄▄█\n" +
                "█   █▄▄▄█       █   █▄█ █       █ █▄▄▄▄▄ \n" +
                "█    ▄▄▄█▄     ▄█    ▄▄▄█       █▄▄▄▄▄  █\n" +
                "█   █     █   █ █   █   █ ██▄██ █▄▄▄▄▄█ █\n" +
                "█▄▄▄█     █▄▄▄█ █▄▄▄█   █▄█   █▄█▄▄▄▄▄▄▄█\n");
        while (true) {
            System.out.println("\nIf you are a student, please enter 1 \n" +
                    "If you are a Faculty, please enter 2 \n" +
                    "If you are a FYP coordinator, please enter 3 \n" +
                    "If you want to exit, please enter 4 \n");

            try {
                int choice = sc.nextInt();
                //login as student
                switch (choice) {
                    case 4:
                        ReadCSV.writeFile();
                        exit(0);
                    case 1:
                        String studentuserid = null;
                        Boolean result = false;
                        Boolean exit2 = false;
                        while (!result) {

                            System.out.println("\nEnter your user ID: ");
                            System.out.println("Enter back to go back");
                            studentuserid = sc.next();
                            if (studentuserid.equals("back")) {
                                exit2 = true;
                                break;
                            }
                            UserFactory userFactoryImpl = new StudentFactory();
                            Student student = (Student) userFactoryImpl.getUser(studentuserid);
                            try {
                                // code that may throw the exception
                                if (student == null) {
                                    throw new IllegalArgumentException("Student not found");
                                }
                            } catch (IllegalArgumentException e) {
                                // handle the exception
                                System.out.println("Wrong userId");
                                continue;
                            }
                            result = student.login();
                        }
                        if (exit2) {
                            break;
                        }
                        UserFactory userFactoryImpl = new StudentFactory();
                        Student student = (Student) userFactoryImpl.getUser(studentuserid);
                        int studentChoice = 0;
                        while (studentChoice != 8) {
                            System.out.println("\nWelcome " + student.getName() + "!");//
                            System.out.println("Please select an option: \n" +
                                    "1. Change your password \n" +
                                    "2. View available projects \n" +
                                    "3. Select the project to send to the coordinator\n" +
                                    "4. View my project \n" +
                                    "5. View requests status and history \n" +
                                    "6. Request to change project title \n" +
                                    "7. Request to deregister FYP \n" +
                                    "8. Exit \n");
                            studentChoice = sc.nextInt();
                            //Student student = StudentRepository.getByID(studentuserid);

                            try {
                                switch (studentChoice) {
                                    case 1:
                                        student.ChangePassword();
                                        break;
                                    case 2:
                                        student.viewAvailableProjects(student);
                                        break;
                                    case 3:
                                        student.sendSelectProjectRequest(student);
                                        break;
                                    case 4:
                                        student.viewProject(student);
                                        break;
                                    case 5:
                                        System.out.println("Your requests: ");
                                        student.viewRequestHistory(studentuserid);
                                        break;
                                    case 6:
                                        student.sendChangeTitleRequest(student);
                                        break;
                                    case 7:
                                        student.sendDeregisterProjectRequest(student);
                                        break;
                                    case 8:
                                        exit = true;
                                        break;
                                    default:
                                        System.out.println("\nPlease enter a number between 1 - 8. Please try again.");
                                        break;
                                }
                            }
                            catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter an integer value.");
                                sc.nextLine(); // consume the invalid input
                            }
                        }
                        if (exit) {
                            break;
                        }
                    case 2:
                        String supervisoruserid = null;
                        boolean superResult = false;
                        boolean exit3 = false;
                        while (!superResult) {
                            System.out.println("\nEnter your user ID: ");
                            System.out.println("Enter back to go back");

                            supervisoruserid = sc.next();
                            if (supervisoruserid.equals("back")) {
                                exit3 = true;
                                break;
                            }
                            userFactoryImpl = new SupervisorFactory();
                            Supervisor supervisor = (Supervisor) userFactoryImpl.getUser(supervisoruserid);
                            try {
                                // code that may throw the exception
                                if (supervisor == null) {
                                    throw new IllegalArgumentException("Supervisor not found");
                                }
                            } catch (IllegalArgumentException e1) {
                                // handle the exception
                                System.out.println("Wrong userId");
                                continue;
                            }

                            superResult = supervisor.login();
                        }
                        if (exit3) {
                            break;
                        }
                        int supervisorChoice = 0;

                        while (supervisorChoice != 6) {
                            boolean pendingRequest = false;
                            userFactoryImpl = new SupervisorFactory();
                            Supervisor supervisor = (Supervisor) userFactoryImpl.getUser(supervisoruserid);
                            System.out.println("\nWelcome " + supervisor.getName() + "!");
                            List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);
                            for (Request request : pendingRequests) {
                                if (request.getToName().equals(supervisor.getName())) {
                                    pendingRequest = true;
                                }
                            }
                            if (pendingRequest) {
                                System.out.println("Please select an option: \n" +
                                        "1. Change your password \n" +
                                        "2. Create/Update title/View projects \n" +//need another switch class
                                        "3. View Student Pending Request New!\n" +
                                        "4. View request history\n" +
                                        "5. Request to transfer student \n" +
                                        "6. Exit\n");
                            } else {
                                System.out.println("Please select an option: \n" +
                                        "1. Change your password \n" +
                                        "2. Create/Update title/View projects \n" +//need another switch class
                                        "3. View Student Pending Request \n" +
                                        "4. View request history\n" +
                                        "5. Request to transfer student \n" +
                                        "6. Exit\n");
                            }
                            try {
                                supervisorChoice = sc.nextInt();
                                switch (supervisorChoice) {
                                    case 1:
                                        supervisor.ChangePassword();
                                        break;
                                    case 2:
                                            System.out.println("\nPlease select an option: \n" +
                                                    "1. Create project\n" +
                                                    "2. Update title\n" +//need another switch class
                                                    "3. View all projects\n" +
                                                    "4. Back \n");
                                            int projectChoice = sc.nextInt();
                                            switch (projectChoice) {
                                                case 1:
                                                    supervisor.createProject(supervisor);
                                                    break;
                                                case 2:
                                                    System.out.println(" ");
                                                    supervisor.updateProject(supervisor);
                                                    break;
                                                case 3:
                                                    supervisor.viewProject();
                                                    break;
                                                case 4:
                                                    break;
                                                default:
                                                    System.out.println("\nPlease enter a number between 1 - 4. Please try again.");
                                                    break;
                                        }
                                        break;
                                    case 3:
                                        supervisor.processChangeTitleRequest();
                                        break;
                                    case 4:
                                        supervisor.viewRequestHistory(supervisoruserid, supervisor);
                                        break;

                                    case 5:
                                        supervisor.transferStudentRequest(supervisor);
                                        break;
                                    case 6:
                                        exit = true;
                                        break;
                                    default:
                                        System.out.println("Please enter a number between 1 - 6. Please try again.");
                                        break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter an integer value.");
                                sc.nextLine(); // consume the invalid input
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
                            System.out.println("\nEnter your user ID: ");
                            System.out.println("Enter back to go back");
                            coorId = sc.next();
                            if (coorId.equals("back")) {
                                exit4 = true;
                                break;
                            }

                            if (!coorId.equals("ASFLI")) {
                                System.out.println("Invalid user ID or password. Please try again.");
                                continue;
                            }
                            Coordinator coordinator = new Coordinator("ASFLI", "Li Fang", "ASFLI@NTU.EDU.SG");
                            coordinatorResult = coordinator.login();
                        }
                        if (exit4) {
                            break;
                        }
                        Coordinator coordinator = new Coordinator("ASFLI", "Li Fang", "ASFLI@NTU.EDU.SG");
                        int coorChoice = 0;
                        while (coorChoice != 5) {
                            boolean pendingRequest = false;
                            List<Request> pendingRequests = RequestRepository.getRequestsbyStatus(RequestStatus.Pending);
                            for (Request request : pendingRequests) {
                                if (request.getToName().equals("Li Fang")) {
                                    pendingRequest = true;
                                }
                            }
                            if (pendingRequest) {
                                System.out.println("\nWelcome " + coordinator.getName() + "!");
                                System.out.println("Please select an option: \n" +

                                        "1. Change your password \n" +
                                        "2. View Projects By filter \n" +//need another switch class
                                        "3. View Request \n" +
                                        "4. View request history\n" +
                                        "5. Exit\n");
                            } else {
                                System.out.println("\nWelcome " + coordinator.getName() + "!");
                                System.out.println("Please select an option: \n" +
                                        "1. changePassword \n" +
                                        "2. View Projects By filter \n" +
                                        "3. View Request \n" +
                                        "4. View request history\n" +
                                        "5. Exit\n");
                            }
                            try {
                                coorChoice = sc.nextInt();
                                switch (coorChoice) {
                                    case 1:
                                        coordinator.ChangePassword();
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
                                        System.out.println("\nPlease enter a number between 1 - 5. Please try again.");
                                        break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter an integer value.");
                                sc.nextLine(); // consume the invalid input
                            }
                        }
                        if (exit) {
                            break;
                        }
                }
            }
            catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer value.");
                    sc.nextLine(); // consume the invalid input
                }
        }
    }
}

