package codesquad.bookkbookk.common.error.exception.jwt;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

public class TokenExpiredException extends ApiException {

    public TokenExpiredException() {
        super(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다.");
    }

}
