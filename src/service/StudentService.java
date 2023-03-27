package service;

import model.Student;
import model.Supervisor;
import repository.StudentRepository;

public class StudentService {
    private static StudentRepository studentRepository;

    public static Student getByID(String id) {
        return studentRepository.getByID(id);
    }
}
