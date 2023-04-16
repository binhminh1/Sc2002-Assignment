package model;

/**
 * Allows overriding and creates student object
 */
import repository.StudentRepository;
public class StudentFactory  implements UserFactory{
    @Override
    public Student getUser(String userID) {
        Student student = StudentRepository.getByID(userID);

        return student;
    }
}
