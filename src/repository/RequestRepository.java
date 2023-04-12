package repository;

import model.Request;
import model.RequestStatus;
import model.RequestType;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {
    public static List<Request> requests = new ArrayList<>();

    public static void addRequest(Request request){
        requests.add(request);
    }

    public static void removeRequest(Request request){
        requests.remove(request);
    }

    public static List<Request> getRequests(){
        return requests;
    }

    public static List<Request> getPendingRequests(){
        List<Request> pendingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getStatus() == RequestStatus.Pending){
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }
    
    public static List<Request> getRequestsbyType(RequestType type){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getType() == type){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }

    public static Request getByID(String id) {
        for (Request request : requests) {
            if (request.getRequestId().equals(id)) {
                return request;
            }
        }
        return null;
    }

    public static List<Request> getRequestsbyStatus(RequestStatus status){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getStatus() == status){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }

    //Outgoing
    public static List<Request> getRequestsByFromId(String fromId){
        List<Request> matchingRequests = new ArrayList<>();
        for(Request request : requests){
            if(request.getFromId().equals(fromId)){
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }

    //Incoming
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
