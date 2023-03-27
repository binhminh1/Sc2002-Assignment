package service;

import model.Supervisor;
import repository.SupervisorRepository;

public class SupervisorService {
    private static SupervisorRepository supervisorRepository;

    public static Supervisor getByID(String id) {
        return supervisorRepository.getByID(id);
    }
}
