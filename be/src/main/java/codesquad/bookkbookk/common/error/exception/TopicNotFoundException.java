package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class TopicNotFoundException extends ApiException{

    public TopicNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 토픽을 찾을 수 없습니다.");
    }

}
