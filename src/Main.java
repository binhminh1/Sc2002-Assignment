import model.Request;
import model.RequestType;

public class Main {
    public static void main(String[] args) {
        String projectID = "projectID";
        String newTitle = "newTitle";
        Request request = new Request(RequestType.changeTitle, projectID, newTitle);
        System.out.println(request);

    }

}