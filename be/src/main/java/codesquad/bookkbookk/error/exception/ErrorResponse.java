package codesquad.bookkbookk.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
