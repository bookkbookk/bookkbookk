package codesquad.bookkbookk.common.error.exception.auth;

import org.springframework.http.HttpStatus;

public class BearerPrefixNotIncludedException extends AuthException {

    public BearerPrefixNotIncludedException() {
        super(HttpStatus.BAD_REQUEST, "Bearer Prefix가 없습니다.", 4011);
    }

}
