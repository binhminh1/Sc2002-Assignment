package model;

import java.util.Date;

public class RequestHistory {
    /**
     * initialize private variables to be used in class
     */
    private RequestStatus status;
    private String updatedDate;

    /**
     * Declaration and assignment of variables
     * @param status (PENDING, DEREGISTERED, REGISTERED)
     * @param updatedDate
     */
    public RequestHistory(RequestStatus status, String updatedDate) {
        this.status = status;
        this.updatedDate = updatedDate;
    }

    /**
     * @return request status
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * @return updated date
     */
    public String getUpdatedDate() {
        return updatedDate;
    }
}
