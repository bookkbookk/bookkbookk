package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends ApiException {

    public RefreshTokenNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "refresh token이 없습니다.");
    }

}
