package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

public class MalformedTokenException extends AuthException {

    public MalformedTokenException(int code) {
        super(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다.", code);
    }

}
