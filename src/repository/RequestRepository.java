package repository;

import model.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {
    public List<Request> requests = new ArrayList<>();

    public void addRequest(Request request){
        requests.add(request);
    }

    public void removeRequest(Request request){
        requests.remove(request);
    }

    public List<Request> getRequests(){
        return requests;
    }
}
