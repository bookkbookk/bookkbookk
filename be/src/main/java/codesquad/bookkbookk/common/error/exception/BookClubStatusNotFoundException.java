package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookClubStatusNotFoundException extends ApiException {

    public BookClubStatusNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Book Club의 Status가 없습니다.");
    }

}
