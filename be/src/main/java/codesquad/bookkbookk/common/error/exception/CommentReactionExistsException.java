package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class CommentReactionExistsException extends ApiException{

    public CommentReactionExistsException() {
        super(HttpStatus.BAD_REQUEST, "멤버가 이미 해당 코멘트에 해당 리액션을 했습니다.");
    }


}
