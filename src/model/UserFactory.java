package model;

/**
 * Allows overriding and creates objects such as student and supervisor
 */
public interface UserFactory {
    public User getUser(String userID);
}
