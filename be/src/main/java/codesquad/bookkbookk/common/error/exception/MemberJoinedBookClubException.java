package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class MemberJoinedBookClubException extends ApiException{

    public MemberJoinedBookClubException() {
        super(HttpStatus.FORBIDDEN, "멤버가 북클럽에 가입했습니다.");
    }

}
