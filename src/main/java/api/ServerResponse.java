package api;

public enum ServerResponse {
    OK(0), ERROR(1),
    ALREADY_EXIST(2), NOT_FOUND(3);

    private final int code;

    private ServerResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
