package codesquad.bookkbookk.common.error.exception.jwt;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.error.exception.ApiException;

public class BearerPrefixNotIncludedException extends ApiException {

    public BearerPrefixNotIncludedException() {
        super(HttpStatus.BAD_REQUEST, "Bearer Prefix가 없습니다.");
    }

}
