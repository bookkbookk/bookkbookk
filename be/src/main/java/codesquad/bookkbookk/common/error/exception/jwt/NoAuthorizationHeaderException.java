package codesquad.bookkbookk.common.error.exception.jwt;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

public class NoAuthorizationHeaderException extends ApiException {

    public NoAuthorizationHeaderException() {
        super(HttpStatus.UNAUTHORIZED, "Authorization Header가 없습니다.");
    }

}
