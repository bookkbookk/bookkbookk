package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.type.TokenError;

public class TokenNotIncludedException extends AuthException {

    public TokenNotIncludedException(TokenError tokenError) {
        super(HttpStatus.BAD_REQUEST, "토큰이 없습니다.", tokenError);
    }

}
