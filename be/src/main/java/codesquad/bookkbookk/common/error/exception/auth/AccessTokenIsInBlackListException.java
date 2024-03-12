package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

public class AccessTokenIsInBlackListException extends AuthException{

    public AccessTokenIsInBlackListException() {
        super(HttpStatus.UNAUTHORIZED, "Access Token이 Black List에 있습니다.", 4011);
    }

}
