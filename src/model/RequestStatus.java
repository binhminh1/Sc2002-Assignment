package model;

/**
 * different types of request status
 * Approved is where the request goes through
 * Pending is where the request have yet to be approved or rejected
 * Rejected is where the request is not approved
 */
public enum RequestStatus {
    Approved,
    Rejected,
    Pending;
}
