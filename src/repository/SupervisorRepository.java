package repository;

import model.Supervisor;

import java.util.ArrayList;
import java.util.List;

/**
 * Extracts supervisors
 */
public class SupervisorRepository {
    /**
     * Creates list of supervisors
     */
    public static List<Supervisor> supervisors = new ArrayList<>();

    /**
     * Allow us to add a supervisor
     * @param supervisor
     */
    public static void addSupervisor(Supervisor supervisor){
        supervisors.add(supervisor);
    }

    /**
     * Allows us to remove a supervisor
     */
    public static void removeSupervisor(Supervisor supervisor){
        supervisors.remove(supervisor);
    }

    /**
     * @return supervisors
     */
    public static List<Supervisor> getSupervisors(){
        return supervisors;
    }

    /**
     * Get supervisor ID
     * @param id
     * @return
     */
    public static Supervisor getByID(String id) {
        for (Supervisor supervisor : supervisors) {
            if (id.equals( supervisor.getUserId())) {
                return supervisor;
            }
        }
        return null;
    }

    /**
     * Get supervisor name
     * @param name
     * @return
     */
    public static Supervisor getByName(String name) {
        for (Supervisor supervisor : supervisors) {
            if (supervisor.getName().equals(name)) {
                return supervisor;
            }
        }
        return null;
    }
}

