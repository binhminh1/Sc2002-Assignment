package repository;


import model.Student;
import model.Supervisor;
import service.SupervisorService;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    public static List<Student> students = new ArrayList<>();

    public static void addSupervisor(Student student){
        students.add(student);
    }

    public static void removeStudent(Student student){
        students.remove(student);
    }

    public static List<Student> getStudents(){
        return students;
    }

    public static Student getByID(String id) {
        for (Student student : students) {
            if (student.getUserId().equals(id)) {
                return student;
            }
        }
        return null;
    }
}