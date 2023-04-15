package model;

/**
 * different request type so that it is differentiable
 * assignProject - student sent a request to coordinator to be assigned to project of choice
 * changeTitle - student sent a request to the supervisor to change the title of allocated project
 * deregister - student sent a request to coordinator to deregister from his allocated FYP project
 * transferStudent - supervisor sent a request to coordinator to transfer student to another supervisor
 */
public enum RequestType {
    assignProject,
    changeTitle,
    deregister,
    transferStudent,
}
