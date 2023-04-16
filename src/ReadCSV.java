import model.*;
import repository.ProjectRepository;
import repository.StudentRepository;
import repository.SupervisorRepository;

import java.io. * ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCSV {
    public static final String delimiter = ",";
    public static List<List<String>> read(String csvFile) {
        try {
            File file = new File(csvFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            List<List<String>> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                list.add(new ArrayList<>(Arrays.asList(line.split(delimiter))));
            }
            br.close();
            return list;
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
    public static void readFile() {
        //csv file to read
        System.out.println("Loading Supervisor List");
        List<List<String>> facultyList = ReadCSV.read(PATH.facultyFile);
        for(List<String> supervisor: facultyList){
            int iend = supervisor.get(1).indexOf("@");
            String subString = null;
            if(iend != -1){
                subString = supervisor.get(1).substring(0 , iend);
            }

            Supervisor supervisor1 = new Supervisor(subString,supervisor.get(0),supervisor.get(1));

            SupervisorRepository.addSupervisor(supervisor1);
        }
        System.out.println("Loading Student List");
        List<List<String>> studentList = ReadCSV.read(PATH.studentFile);

        for(List<String> student: studentList){
            int iend = student.get(1).indexOf("@");
            String subString = null;
            if(iend != -1){
                subString = student.get(1).substring(0 , iend);
            }
            Student student1 = new Student(subString,student.get(0), student.get(1));
            StudentRepository.addStudent(student1);
        }

        System.out.println("Loading Project List");
        List<List<String>> projectList = ReadCSV.read(PATH.projectFile);
        int o = 1;
        for(List<String> project: projectList){


            Supervisor supervisor = SupervisorRepository.getByName(project.get(0));

            Project project1 = new Project(String.valueOf(o),supervisor.getName(), project.get(1));

            String studentID = project.get(2);

            ProjectRepository.addProject(project1);

            if (studentID != null && !studentID.equals("null"))
            {
                project1.setStatus(ProjectStatus.ALLOCATED);
                project1.setStudentId(studentID);
                Student student99 = StudentRepository.getByID(studentID);
                student99.changeStatus(StudentStatus.REGISTERED);
            }

            o++;
        }

    }

    public static void writeFile() {
        try {
            FileWriter writer = new FileWriter(PATH.projectFile); // open the file in append mode

            List<Project> projectList = ProjectRepository.getProjects();

            // Write project data to file
            for (Project project : projectList) {
                writer.write(project.getSupervisorName() + ",");
                writer.write(project.getProjectTitle() + ",");
                writer.write(project.getStudentId()+"\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
