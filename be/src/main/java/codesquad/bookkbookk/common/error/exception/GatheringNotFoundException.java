package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class GatheringNotFoundException extends ApiException {

    public GatheringNotFoundException() {
        super(HttpStatus.NOT_FOUND, "id에 해당하는 Gathering이 없습니다.");
    }

}
