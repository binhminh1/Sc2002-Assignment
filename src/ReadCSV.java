import model.Project;
import model.Student;
import model.Supervisor;
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
        List<List<String>> facultyList = ReadCSV.read("src\\faculty_list.csv");
        for(List<String> supervisor: facultyList){
            int iend = supervisor.get(1).indexOf("@");
            String subString = null;
            if(iend != -1){
                subString = supervisor.get(1).substring(0 , iend);
            }
            Supervisor supervisor1 = new Supervisor(subString,supervisor.get(0),supervisor.get(1));
            SupervisorRepository.addSupervisor(supervisor1);
        }
        System.out.println("Loading Project List");
        List<List<String>> projectList = ReadCSV.read("src\\rollover project.csv");
        int o = 1;
        for(List<String> project: projectList){
            Project project1 = new Project(String.valueOf(o),project.get(0), project.get(1));
            ProjectRepository.addProject(project1);

            o++;
        }
        System.out.println("Loading Student List");
        List<List<String>> studentList = ReadCSV.read("src\\student list.csv");

        for(List<String> student: studentList){
            int iend = student.get(1).indexOf("@");
            String subString = null;
            if(iend != -1){
                subString = student.get(1).substring(0 , iend);
            }
            Student student1 = new Student(subString,student.get(0), student.get(1));
            StudentRepository.addStudent(student1);
        }

    }
}
