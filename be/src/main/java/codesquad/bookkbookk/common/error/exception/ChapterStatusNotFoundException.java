package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class ChapterStatusNotFoundException extends ApiException{

    public ChapterStatusNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Chapter Status를 찾을 수 없습니다.");
    }

}
