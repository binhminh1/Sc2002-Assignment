package model;
import repository.SupervisorRepository;

/**
 * Allows overriding and creates supervisor object
 */
public class SupervisorFactory implements UserFactory{
    public User getUser(String userID) {
        Supervisor supervisor = SupervisorRepository.getByID(userID);
        return supervisor;
    }
}
