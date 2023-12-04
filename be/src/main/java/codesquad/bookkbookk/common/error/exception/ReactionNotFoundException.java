package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class ReactionNotFoundException extends ApiException {

    public ReactionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "이름에 해당하는 Reaction이 없습니다.");
    }

}
