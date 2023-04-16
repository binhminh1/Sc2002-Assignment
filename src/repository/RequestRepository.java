package repository;

import model.Request;
import model.RequestStatus;
import model.RequestType;

import java.util.ArrayList;
import java.util.List;

/**
 * Extract requests
 */
public class RequestRepository {
    /**
     * Creates list of requests
     */
    public static List<Request> requests = new ArrayList<>();

    /**
     * Allow us to add a request
     * @param request
     */
    public static void addRequest(Request request){
        requests.add(request);
    }

    /**
     * Allows us to remove a request
     * @param request
     */
    public static void removeRequest(Request request){
        requests.remove(request);
    }

    /**
     * @return requests
     */
    public static List<Request> getRequests(){
        return requests;
    }

    /**
     * @return requests that are pending
     */
    public static List<Request> getPendingRequests(){
        List<Request> pendingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getStatus() == RequestStatus.Pending){
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    /**
     * categorise requests by its type 
     * @param type (assignProject, deregister, transferStudent, changeTitle)
     * @return
     */
    public static List<Request> getRequestsbyType(RequestType type){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getType() == type){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }
    
    /**
     * get the request ID
     * @param id
     * @return
     */
    public static Request getByID(String id) {
        for (Request request : requests) {
            if (request.getRequestId().equals(id)) {
                return request;
            }
        }
        return null;
    }

    /**
     * categorise requests by its status
     * @param status (PENDING, APPROVED, REJECTED)
     * @return
     */
    public static List<Request> getRequestsbyStatus(RequestStatus status){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getStatus() == status){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }

    /**
     * categorise requests by its type (outgoing)
     * @param status
     * @return
     */
    public static List<Request> getRequestsByFromId(String fromId){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getFromId().equals(fromId)){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }

    /**
     * categorise requests by its type (incoming)
     * @param toName
     * @return
     */
    public static List<Request> getRequestsByToName(String toName){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getToName().equals(toName)){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }
}
