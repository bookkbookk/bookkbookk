package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class LastInsertIdDoesNotExistException extends ApiException{

    public LastInsertIdDoesNotExistException() {
        super(HttpStatus.BAD_REQUEST, "마지막 insert 아이디가 존재하지 않습니다.");
    }

}
