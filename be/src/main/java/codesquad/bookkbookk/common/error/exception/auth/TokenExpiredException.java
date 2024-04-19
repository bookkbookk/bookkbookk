package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.type.TokenError;

public class TokenExpiredException extends AuthException {

    public TokenExpiredException(TokenError tokenError) {
        super(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다.", tokenError);
    }

}
