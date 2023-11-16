package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

public class TokenNotIncludedException extends AuthException {

    public TokenNotIncludedException(int code) {
        super(HttpStatus.BAD_REQUEST, "토큰이 없습니다.", code);
    }

}
