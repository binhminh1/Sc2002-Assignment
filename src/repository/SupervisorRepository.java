package repository;

import model.Supervisor;
import service.SupervisorService;

import java.util.ArrayList;
import java.util.List;

public class SupervisorRepository {
    public static List<Supervisor> supervisors = new ArrayList<>();

    public static void addSupervisor(Supervisor supervisor){
        supervisors.add(supervisor);
    }

    public static void removeSupervisor(Supervisor supervisor){
        supervisors.remove(supervisor);
    }

    public static List<Supervisor> getSupervisors(){
        return supervisors;
    }

    public static Supervisor getByID(String id) {
        for (Supervisor supervisor : supervisors) {
            if (id.equals( supervisor.getUserId())) {
                return supervisor;
            }
        }
        return null;
    }
    public static Supervisor getByName(String name) {
        for (Supervisor supervisor : supervisors) {
            if (supervisor.getName().equals(name)) {
                return supervisor;
            }
        }
        return null;
    }
}

