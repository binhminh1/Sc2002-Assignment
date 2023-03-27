package repository;

import model.Supervisor;
import service.SupervisorService;

import java.util.ArrayList;
import java.util.List;

public class SupervisorRepository {
    public List<Supervisor> supervisors = new ArrayList<>();

    public void addSupervisor(Supervisor supervisor){
        supervisors.add(supervisor);
    }

    public void removeSupervisor(Supervisor supervisor){
        supervisors.remove(supervisor);
    }

    public List<Supervisor> getSupervisors(){
        return supervisors;
    }

    public Supervisor getByID(String id) {
        for (Supervisor supervisor : supervisors) {
            if (supervisor.getUserId().equals(id)) {
                return supervisor;
            }
        }
        return null;
    }
}

