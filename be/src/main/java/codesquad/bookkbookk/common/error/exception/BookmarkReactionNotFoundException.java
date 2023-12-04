package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookmarkReactionNotFoundException extends ApiException{

    public BookmarkReactionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Bookmark의 Reaction이 없습니다.");
    }

}
