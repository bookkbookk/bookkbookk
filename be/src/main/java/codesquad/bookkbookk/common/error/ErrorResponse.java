package codesquad.bookkbookk.common.error;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int code;
    private final HttpStatus status;
    private final String message;

    private ErrorResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse from(ApiException exception) {
        return new ErrorResponse(exception.getStatus(), exception.getMessage());
    }

}
