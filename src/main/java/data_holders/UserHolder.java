package data_holders;

import model.User;

public class UserHolder {
    private User user;
    private static final UserHolder INSTANCE = new UserHolder();

    private UserHolder() {}

    public static UserHolder getINSTANCE() {
        return INSTANCE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User currentUser) {
        this.user = currentUser;
    }
}
