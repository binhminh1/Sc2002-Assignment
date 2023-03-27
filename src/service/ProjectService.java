package service;

import model.Project;
import repository.ProjectRepository;

public class ProjectService {
    private static ProjectRepository projectRepository;

    public static Project getByID(String id) {
        return projectRepository.getByID(id);
    }
}
