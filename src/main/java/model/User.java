package model;

public class User {
    private final int userId;
    private final String username;
    private final String password;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }


    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
