package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class MemberNotInBookClubException extends ApiException {

    public MemberNotInBookClubException() {
        super(HttpStatus.FORBIDDEN, "해당 북클럽의 권한이 없습니다.");
    }

}
