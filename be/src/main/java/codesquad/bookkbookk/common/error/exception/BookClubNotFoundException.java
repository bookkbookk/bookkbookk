package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class BookClubNotFoundException extends ApiException{

    public BookClubNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 북클럽을 찾을 수 없습니다.");
    }

}
