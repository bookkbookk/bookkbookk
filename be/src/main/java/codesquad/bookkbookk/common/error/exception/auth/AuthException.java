package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

import lombok.Getter;

@Getter
public class AuthException extends ApiException {

    private final int code;

    public AuthException(HttpStatus status, String message, int code) {
        super(status, message);
        this.code = code;
    }

}
