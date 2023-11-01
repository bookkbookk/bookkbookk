package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class MemberIsNotCommentWriterException extends ApiException {

    public MemberIsNotCommentWriterException() {
        super(HttpStatus.FORBIDDEN, "사용자가 코멘트 작성자가 아닙니다.");
    }

}
