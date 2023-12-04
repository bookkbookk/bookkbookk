package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class CommentReactionNotFoundException extends ApiException{

    public CommentReactionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Comment의 Reaction이 없습니다.");
    }

}
