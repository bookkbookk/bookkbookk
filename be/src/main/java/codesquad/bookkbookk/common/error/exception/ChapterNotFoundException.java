package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class ChapterNotFoundException extends ApiException {

    public ChapterNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 챕터를 찾을 수 없습니다.");
    }

}
