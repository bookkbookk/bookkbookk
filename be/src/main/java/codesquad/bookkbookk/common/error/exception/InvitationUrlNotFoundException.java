package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class InvitationUrlNotFoundException extends ApiException {

    public InvitationUrlNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 북클럽의 초대 URL을 찾을 수 없습니다.");
    }

}
