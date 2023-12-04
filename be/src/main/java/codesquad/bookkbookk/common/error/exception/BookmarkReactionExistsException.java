package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookmarkReactionExistsException extends ApiException{

    public BookmarkReactionExistsException() {
        super(HttpStatus.BAD_REQUEST, "멤버가 이미 해당 북마크에 해당 리액션을 했습니다.");
    }

}
