package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;
import codesquad.bookkbookk.common.type.TokenError;

import lombok.Getter;

@Getter
public class AuthException extends ApiException {

    private final TokenError tokenError;

    public AuthException(HttpStatus status, String message, TokenError tokenError) {
        super(status, message);
        this.tokenError = tokenError;
    }

}
