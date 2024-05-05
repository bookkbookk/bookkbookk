package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.type.TokenError;

public class MalformedTokenException extends AuthException {

    public MalformedTokenException(TokenError tokenError) {
        super(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다.", tokenError);
    }

}
