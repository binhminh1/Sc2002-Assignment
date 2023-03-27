package repository;


import model.Student;
import model.Supervisor;
import service.SupervisorService;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    public List<Student> students = new ArrayList<>();

    public void addSupervisor(Student student){
        students.add(student);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public List<Student> getStudents(){
        return students;
    }

    public Student getByID(String id) {
        for (Student student : students) {
            if (student.getUserId().equals(id)) {
                return student;
            }
        }
        return null;
    }
}