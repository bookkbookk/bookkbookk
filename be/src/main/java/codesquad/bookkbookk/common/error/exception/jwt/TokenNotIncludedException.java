package codesquad.bookkbookk.common.error.exception.jwt;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

public class TokenNotIncludedException extends ApiException {

    public TokenNotIncludedException() {
        super(HttpStatus.BAD_REQUEST, "토큰이 없습니다.");
    }

}
