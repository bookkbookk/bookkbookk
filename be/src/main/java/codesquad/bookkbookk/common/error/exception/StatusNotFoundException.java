package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class StatusNotFoundException extends ApiException{

    public StatusNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Status를 찾을 수 없습니다.");
    }

}
