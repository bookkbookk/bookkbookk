package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends AuthException {

    public TokenExpiredException(int code) {
        super(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다.", code);
    }

}
