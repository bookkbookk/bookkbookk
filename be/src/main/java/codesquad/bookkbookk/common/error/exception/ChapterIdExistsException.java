package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class ChapterIdExistsException extends ApiException{

    public ChapterIdExistsException() {
        super(HttpStatus.BAD_REQUEST, "Chapter Id가 존재합니다.");
    }

}
