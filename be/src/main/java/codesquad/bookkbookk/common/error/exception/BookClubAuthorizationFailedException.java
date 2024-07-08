package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookClubAuthorizationFailedException extends ApiException{

    public BookClubAuthorizationFailedException() {
        super(HttpStatus.UNAUTHORIZED, "북클럽 인가에 실패했습니다.");
    }

}
