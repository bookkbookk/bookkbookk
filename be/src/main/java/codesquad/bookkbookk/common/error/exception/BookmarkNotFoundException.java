package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookmarkNotFoundException extends ApiException {

    public BookmarkNotFoundException() {
        super(HttpStatus.NOT_FOUND, "북마크가 존재하지 않습니다.");
    }

}
