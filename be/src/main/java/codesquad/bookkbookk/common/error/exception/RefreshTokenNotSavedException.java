package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class RefreshTokenNotSavedException extends ApiException {

    public RefreshTokenNotSavedException() {
        super(HttpStatus.NOT_FOUND, "refreshToken에 해당하는 멤버가 없습니다.");
    }

}
