import java.util.Objects;

public class Student implements User{
    private String UserId;
    private String Password;
    private String Email;
    private String Name;
    private String Project;

    @Override
    public void changePassword(String password){
        Password = password;
    }
    public boolean login(String userid, String password){
        return (Objects.equals(UserId, userid) && Objects.equals(Password, password));

    }



}
