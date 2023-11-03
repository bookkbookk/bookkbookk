package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final int code;
    private final HttpStatus status;
    private final String message;

    public ApiException(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
