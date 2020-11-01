package data_holders;

public class UserHolder {
    private Integer userId;
    private static final UserHolder INSTANCE = new UserHolder();

    private UserHolder() {}

    public static UserHolder getINSTANCE() {
        return INSTANCE;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
