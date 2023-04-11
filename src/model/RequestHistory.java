package model;

import java.util.Date;

public class RequestHistory {
    /**
     * initialize private variables to be used in class
     */
    private RequestStatus status;
    private String updatedDate;

    /**
     * 
     * @param status
     * @param updatedDate
     */
    public RequestHistory(RequestStatus status, String updatedDate) {
        this.status = status;
        this.updatedDate = updatedDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }
}
