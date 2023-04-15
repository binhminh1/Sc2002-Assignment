package repository;


import model.Student;
import model.Supervisor;


import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    /**
     * Creates list of students
     */
    public static List<Student> students = new ArrayList<>();

    /**
     * Allow us to add a student
     * @param student
     */
    public static void addStudent(Student student){
        students.add(student);
    }

    /**
     * Allows us to remove a student
     * @param student
     */
    public static void removeStudent(Student student){
        students.remove(student);
    }

    /**
     * @return students
     */
    public static List<Student> getStudents(){
        return students;
    }

    /**
     * get student ID
     */
    public static Student getByID(String id) {
        for (Student student : students) {
            String userid=student.getUserId();
            if (student.getUserId().equals(id)) {
                return student;
            }
        }
        return null;
    }
}