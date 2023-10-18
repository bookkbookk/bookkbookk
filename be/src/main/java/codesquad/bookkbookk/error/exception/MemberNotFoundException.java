package codesquad.bookkbookk.error.exception;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

public class MemberNotFoundException extends ApiException{

    public MemberNotFoundException() {
        super(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.");
    }

}
