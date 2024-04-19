package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.type.TokenError;

public class AccessTokenIsInBlackListException extends AuthException{

    public AccessTokenIsInBlackListException() {
        super(HttpStatus.UNAUTHORIZED, "Access Token이 Black List에 있습니다.", TokenError.ACCESS_TOKEN);
    }

}
