package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class MalformedIsbnException extends ApiException {

    public MalformedIsbnException() {
        super(HttpStatus.BAD_REQUEST, "올바르지 않은 ISBN입니다.");
    }

}
