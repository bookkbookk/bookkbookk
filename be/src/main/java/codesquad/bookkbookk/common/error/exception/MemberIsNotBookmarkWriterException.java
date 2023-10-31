package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class MemberIsNotBookmarkWriterException extends ApiException {

    public MemberIsNotBookmarkWriterException() {
        super(HttpStatus.FORBIDDEN, "사용자가 북마크 작성자가 아닙니다.");
    }

}
