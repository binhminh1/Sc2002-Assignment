package model;

import java.util.Date;


public class RequestHistory {
    private RequestStatus status;
    private String updatedDate;

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
