package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ApiException {

    public CommentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "코멘트가 없습니다.");
    }

}
