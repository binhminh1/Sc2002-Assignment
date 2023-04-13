package model;

/**
 * The different statuses that each project can have.<!-- -->
 * Students can choose AVAILABLE projects
 * while waiting for the cooridnator to approve the allocation, the project is RESERVED
 * the project is ALLOCATED after the coordinator approves the request
 * the project is UNAVAILABLE if the suepervisor already have 2 projects under his ID.
 */
public enum ProjectStatus {
    AVAILABLE,
    RESERVED,
    UNAVAILABLE,
    ALLOCATED
}
