package codesquad.bookkbookk.common.error;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.error.exception.auth.AuthException;

import lombok.Getter;

@Getter
public class ErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer code;
    private final HttpStatus status;
    private final String message;

    private ErrorResponse(Integer code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse from(ApiException exception) {
        return new ErrorResponse(null, exception.getStatus(), exception.getMessage());
    }

    public static ErrorResponse from(AuthException exception) {
        return new ErrorResponse(exception.getCode(), exception.getStatus(), exception.getMessage());
    }

}
