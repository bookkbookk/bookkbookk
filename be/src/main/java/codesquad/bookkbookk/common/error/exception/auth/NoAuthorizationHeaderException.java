package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.type.TokenError;

public class NoAuthorizationHeaderException extends AuthException {

    public NoAuthorizationHeaderException() {
        super(HttpStatus.UNAUTHORIZED, "Authorization Header가 없습니다.", TokenError.ACCESS_TOKEN);
    }

}
