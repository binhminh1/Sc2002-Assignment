package model;
import repository.SupervisorRepository;
public class SupervisorFactory implements UserFactory{
    public User getUser(String userID) {
        Supervisor supervisor = SupervisorRepository.getByID(userID);
        if (supervisor == null) {
            throw new IllegalArgumentException("Supervisor not found");
        }
        return supervisor;
    }
}
