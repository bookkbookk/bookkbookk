package codesquad.bookkbookk.common.type;

public enum TokenError {
    ACCESS_TOKEN(4011), REFRESH_TOKEN(4012);

    private final int code;

    TokenError(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
