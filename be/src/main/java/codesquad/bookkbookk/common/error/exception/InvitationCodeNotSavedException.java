package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class InvitationCodeNotSavedException extends ApiException{

    public InvitationCodeNotSavedException() {
        super(HttpStatus.NOT_FOUND, "invitation code가 저장되어 있지 않습니다.");
    }

}
