package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class TopicIdExistsException extends ApiException{

    public TopicIdExistsException() {
        super(HttpStatus.BAD_REQUEST, "Topic Id가 존재합니다.");
    }

}
