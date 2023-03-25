package model;

public interface User {
    public void changePassword(String password);
    public boolean login(String userid, String password);
}
