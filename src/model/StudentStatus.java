package model;

/**
 * Status of students and their projects
 * Pending - student sent a request to be in project
 * Deregistered - student deregistered from the project and cannot select it again
 * Registered - coordinator approved student to select the project
 */
public enum StudentStatus {
    PENDING,
    DEREGISTERED,
    REGISTERED,
    UNREGISTERED
}
