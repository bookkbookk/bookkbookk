package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends ApiException{

    public BookNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 책을 찾을 수 없습니다.");
    }

}
