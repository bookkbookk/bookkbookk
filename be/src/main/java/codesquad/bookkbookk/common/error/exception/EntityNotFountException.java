package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

import codesquad.bookkbookk.common.type.EntityType;

public class EntityNotFountException extends ApiException {

    public EntityNotFountException(EntityType type) {
        super(HttpStatus.NOT_FOUND, type.getErrorMessage());
    }

}
