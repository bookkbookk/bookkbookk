package codesquad.bookkbookk.common.error.exception.jwt;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

public class MalformedTokenException extends ApiException {

    public MalformedTokenException() {
        super(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다.");
    }

}
